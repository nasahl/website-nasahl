package nasahl.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class WordWriter {

    private final static String wordSnippet = "word.txt";

    private final List<Category> categories;

    WordWriter(final List<Category> categories) {
        this.categories = categories;
    }

    void writeToWord() throws IOException {
        final File wordFile = new File("target/" + wordSnippet);
        final BufferedWriter writer = new BufferedWriter(new FileWriter(wordFile));

        for (final Category category : categories) {
            if (category.isVisible()) {
                writer.write(category.name() + "\n");
                for (final List<Skill> skillLine : WriterUtils.transformToLines(category.skills())) {
                    final String line = skillLine.stream()
                            .map(skill -> skill.name() + "  " + "*".repeat((int) skill.level()))
                            .collect(Collectors.joining("\t"));
                    writer.write(line + "\n");
                }
            }
        }

        writer.close();
        System.out.println("Word snippet written to " + wordFile.getAbsolutePath());
    }
}
