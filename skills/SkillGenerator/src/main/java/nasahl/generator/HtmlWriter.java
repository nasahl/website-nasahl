package nasahl.generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class HtmlWriter {

    private final SkillData data;

    HtmlWriter(final SkillData data) {
        this.data = data;
    }

    void writeToHtml() throws IOException {
        final BufferedWriter writer = new BufferedWriter(new FileWriter(FileConstants.indexFile));

        final List<String> fileLines = Files.readAllLines(FileConstants.originalIndexFile.toPath());

        final String skillStartTag = "Skill content - Start";
        final String skillEndTag = "Skill content - End";
        int phase = 0;
        for (final String line : fileLines) {
            if (phase == 0) {
                writeln(writer, 0, line);
                if (line.contains(skillStartTag)) {
                    phase = 1;
                    writeSkills(writer);
                }
            } else if (phase == 1) {
                if (line.contains(skillEndTag)) {
                    writeln(writer, 0, line);
                    phase = 2;
                }
            } else if (phase == 2) {
                writeln(writer, 0, line);
            }
        }

        writer.close();
        System.out.println("Modified index file written to " + FileConstants.indexFile.getAbsolutePath());
    }

    private void writeSkills(final BufferedWriter writer) throws IOException {
        writeln(writer, 0, "");
        for (final Category category : data.skills()) {
            if (category.isVisible()) {
                writeln(writer, 0, "");
                writeln(writer, 3, "<div class=\"subheading mb-3\">" + category.name() + "</div>");
                writeln(writer, 0, "");
                for (final List<Skill> skillLine : WriterUtils.transformToLines(category.skills())) {
                    writeln(writer, 3, "<div class=\"row\">");
                    for (final Skill skill : skillLine) {
                        writeln(writer, 4, "<div class=\"col-xl-4\">" + skill.name() + " &nbsp;&nbsp;<span class = \"fa my-star-" + skill.level() + "\"></span>" + "</div>");
                    }
                    writeln(writer, 2, "</div>");
                    writeln(writer, 2, "");
                }
                writeln(writer, 3, "<p></p>");
            }
        }
        writeln(writer, 0, "");
        writeln(writer, 3, "<div class=\"small mt-5\">aktualisiert: " + data.refreshDate() + "</div>");
        writeln(writer, 3, "<a href=\""+FileConstants.excelFileName+"\" class=\"small\">Download im Excelformat</a>");
        writeln(writer, 0, "");
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
