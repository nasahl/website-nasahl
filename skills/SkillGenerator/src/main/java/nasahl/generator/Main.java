package nasahl.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    private final static String excelFileName = "skills.xlsx";
    private final static String htmlSnippet = "html.txt";

    public static void main(final String[] args) throws IOException {
        final File excelFile = findFile(new File(".").getAbsoluteFile());

        final List<Category> categories = new Parser().parseFile(excelFile);
        System.out.println(categories);

        new HtmlWriter(categories).writeToHtml();
        System.out.println("Add the html content into the labels 'Skill content'");

        new WordWriter(categories).writeToWord();
        System.out.println("Add the word content into the word document");
    }

    private static File findFile(final File directory) {
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalStateException("Can't find file " + excelFileName);
        }
        System.out.println("Directory: " + directory.getAbsolutePath());
        final File maybeFile = new File(directory, excelFileName);
        System.out.println("maybeFile: " + maybeFile.getAbsolutePath());
        if (maybeFile.isFile()) {
            return maybeFile;
        }
        return findFile(directory.getParentFile());
    }

}