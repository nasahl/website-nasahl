package nasahl.generator;

import java.util.ArrayList;
import java.util.List;

public class WriterUtils {

  static List<List<Skill>> transformToLines(final List<Skill> skills) {
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
