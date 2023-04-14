package nasahl.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    private final static String excelFileName = "skills.xlsx";

    public static void main(final String[] args) throws IOException {
        final File excelFile = FileUtils.findFile(excelFileName);

        final List<Category> categories = new Parser().parseFile(excelFile);
        System.out.println(categories);

        new HtmlWriter(categories).writeToHtml();
        System.out.println("Add the html content into the labels 'Skill content'");

        new WordWriter(categories).writeToWord();
        System.out.println("Add the word content into the word document");
    }

}