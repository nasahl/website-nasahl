package nasahl.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Parser {

    private static Optional<String> contentOf(final Row row, final int col) {
        final Cell cell = row.getCell(col);
        if (cell == null)
            return Optional.empty();
        final String value = cell.getStringCellValue();
        if (value == null || value.trim().length() == 0)
            return Optional.empty();
        return Optional.of(value);
    }

    private static Optional<Long> longContentOf(final Row row, final int col) {
        return Optional.ofNullable(row.getCell(col))
                .map(Cell::getNumericCellValue).map(Math::round);
    }

    private static boolean isVisible(final Row row) {
        return longContentOf(row, 3).map(c -> c != 0L).orElse(true);
    }

    SkillData parseFile(final File excelFile) throws IOException {
        final FileInputStream file = new FileInputStream(excelFile);
        final Workbook workbook = new XSSFWorkbook(file);
        final Sheet sheet = workbook.getSheetAt(0);

        final List<Category> categories = new ArrayList<>();
        int count = 0;
        String currentCategory = null;
        boolean currentCategoryIsVisible = true;
        final List<Skill> skills = new ArrayList<>();
        boolean headerFinished = false;
        String refreshDate = "";
        for (final Row cells : sheet) {
            if (!headerFinished) {
                if (contentOf(cells, 0).filter("Aktualisiert"::equals).isPresent()) {
                    refreshDate = contentOf(cells, 1).orElse("");
                }
                if (contentOf(cells, 0).filter("Themenbereich"::equals).isPresent()) {
                    headerFinished = true;
                }
                continue;
            }
            final Optional<String> category = contentOf(cells, 0);
            if (category.isPresent()) {
                collectCategory(categories, currentCategory, List.copyOf(skills), currentCategoryIsVisible);
                skills.clear();
                currentCategory = category.get();
                currentCategoryIsVisible = isVisible(cells);
            } else {
                final Optional<String> skill = contentOf(cells, 1);
                skill.ifPresent(name -> skills.add(new Skill(name, longContentOf(cells, 2).orElse(-1L), isVisible(cells))));
            }

        }
        collectCategory(categories, currentCategory, skills, currentCategoryIsVisible);

        return new SkillData(refreshDate, categories);
    }

    private void collectCategory(final List<Category> categories, final String currentCategory, final List<Skill> skills, final boolean isVisible) {
        if (currentCategory != null) {
            categories.add(new Category(currentCategory, skills, isVisible));
        }
    }

}
