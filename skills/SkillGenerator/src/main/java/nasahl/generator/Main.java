package nasahl.generator;

import java.io.File;
import java.io.IOException;

public class Main {

  public static void main(final String[] args) throws IOException {
    final File excelFile = new File(FileConstants.excelFileName);

    final SkillData data = new Parser().parseFile(excelFile);
    System.out.println(data);

    new HtmlWriter(data).writeToHtml();
    System.out.println("Add the html content into the labels 'Skill content'");

    new WordWriter(data).writeToWord();
    System.out.println("Add the word content into the word document");
  }
}
