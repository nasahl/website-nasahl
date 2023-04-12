package nasahl.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class HtmlWriter {

    private final static String htmlSnippet = "html.txt";

    private final List<Category> categories;

    HtmlWriter(final List<Category> categories) {
        this.categories = categories;
    }

    void writeToHtml() throws IOException {
        final File htmlFile = new File("target/" + htmlSnippet);
        final BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile));

        for (final Category category : categories) {
            if (category.isVisible()) {
                writeln(writer, 0, "");
                writeln(writer, 3, "<div class=\"subheading mb-3\">" + category.name() + "</div>");
                writeln(writer, 0, "");
                writeln(writer, 3, "<div class=\"row\">");
                for (final List<Skill> skillLine : transformToLines(category.skills())) {
                    writeln(writer, 4, "<div class=\"col\">");
                    writeln(writer, 5, "<ul class=\"fa-ul mb-0\">");
                    for (final Skill skill : skillLine) {
                        writeln(writer, 6, "<li>" + skill.name() + " (" + "*".repeat((int) skill.level()) + ")</li>");
                    }
                    writer.write("");
                    writer.write("");
                    writer.write("");
                    writeln(writer, 5, "</ul>");
                    writeln(writer, 4, "</div>");
                    writeln(writer, 0, "");
                }
                writeln(writer, 3, "</div>");
                writeln(writer, 3, "<p></p>");

            }
        }

        writer.close();
        System.out.println("Html snippet written to " + htmlFile.getAbsolutePath());
    }

    private void writeln(final BufferedWriter writer, final int i, final String s) throws IOException {
        writer.write(" ".repeat(4 * i) + s + "\n");
    }

    private static List<List<Skill>> transformToLines(final List<Skill> skills) {
        final int nrColumns = 3;
        final List<List<Skill>> result = new ArrayList<>();
        IntStream.range(0, 3).forEach(i -> result.add(new ArrayList<>()));
        int count = 0;
        for (final Skill skill : skills) {
            if (skill.isVisible()) {
                result.get(count++ % nrColumns).add(skill);
            }
        }
        return result;
    }


}
