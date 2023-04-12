package nasahl.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
                for (final List<Skill> skillLine : transformToLines(category.skills())) {
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

    private static List<List<Skill>> transformToLines(final List<Skill> skills) {
        final List<List<Skill>> result = new ArrayList<>();
        int count = 0;
        final List<Skill> skillLine = new ArrayList<>();
        for (final Skill skill : skills) {
            if (skill.isVisible()) {
                if (count++ == 3) {
                    result.add(List.copyOf(skillLine));
                    skillLine.clear();
                    count = 1;
                }
                skillLine.add(skill);
            }
        }
        if (!skillLine.isEmpty()) {
            result.add(List.copyOf(skillLine));
        }
        return result;
    }


}
