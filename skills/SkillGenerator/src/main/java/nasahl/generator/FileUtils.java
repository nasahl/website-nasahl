package nasahl.generator;

import java.io.File;

public class FileUtils {


    static File findFile(final String fileName) {
        return findFile(new File(".").getAbsoluteFile(), fileName);
    }

    private static File findFile(final File directory, final String fileName) {
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalStateException("Can't find file " + fileName);
        }
        final File maybeFile = new File(directory, fileName);
        if (maybeFile.isFile()) {
            return maybeFile;
        }
        return findFile(directory.getParentFile(), fileName);
    }
}
