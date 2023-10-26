package nasahl.generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class WordWriter {

  private static final String wordSnippet = "word.txt";

  private final List<Category> skills;

  WordWriter(final SkillData data) {
    this.skills = data.skills();
  }

  void writeToWord() throws IOException {
    final BufferedWriter writer = new BufferedWriter(new FileWriter(FileConstants.wordSnippetFile));

    for (final Category category : skills) {
      if (category.isVisible()) {
        writer.write(category.name() + "\n");
        for (final List<Skill> skillLine : WriterUtils.transformToLines(category.skills())) {
          final String line =
              skillLine.stream()
                  .map(skill -> skill.name() + "  " + "*".repeat((int) skill.level()))
                  .collect(Collectors.joining("\t"));
          writer.write(line + "\n");
        }
      }
    }

    writer.close();
    System.out.println(
        "Word snippet written to " + FileConstants.wordSnippetFile.getAbsolutePath());
  }
}
