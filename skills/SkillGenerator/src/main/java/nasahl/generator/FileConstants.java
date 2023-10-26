package nasahl.generator;

import java.io.File;

public class FileConstants {
  private static final String indexFileName = "index.html";
  private static final String wordSnippetFileName = "word.txt";
  private static final String generatorPath = "skills/SkillGenerator/";

  static final  String excelFileName = "downloads/skillsNasahl.xlsx";
  static final File originalIndexFile = new File(indexFileName);
  static final File indexFile = new File(generatorPath + "target/" + indexFileName);
  static final File wordSnippetFile = new File(generatorPath + "target/" + wordSnippetFileName);
}
