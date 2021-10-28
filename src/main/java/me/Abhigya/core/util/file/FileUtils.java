package me.Abhigya.core.util.file;

import org.apache.commons.lang.Validate;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/** Class for dealing with files. */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * Gets all the elements within desired {@code directory}, including the files within the
     * sub-directories of the desired directory.
     *
     * <p>
     *
     * @param directory Desired directory
     * @return All the elements within the directory and its sub-directories
     */
    public static Set<File> getElements(File directory) {
        Validate.isTrue(directory.isDirectory(), "the provided file is not a valid directory!");

        final Set<File> elements = new HashSet<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                elements.addAll(getElements(file)); // recursive.
            } else {
                elements.add(file);
            }
        }
        return elements;
    }

    /**
     * Gets the length of the provided {@code directory}. The returned value represents the sum of
     * the lengths of all the elements within the desired directory.
     *
     * <p>Also if the provided {@link File} is a file ({@link File#isFile()}), then the returned
     * value will be the length the that file.
     *
     * <p>
     *
     * @param directory Desired directory
     * @return Sum of the sum of the lengths of all the elements within the directory
     */
    public static long getLength(File directory) {
        if (directory.isFile()) {
            return directory.length();
        }

        final Set<File> elements = getElements(directory);
        long length = 0L;
        return elements.stream()
                .map((e) -> e.length())
                .reduce(length, (accumulator, _item) -> accumulator + _item);
    }

    /**
     * Get the file type
     *
     * <p>
     *
     * @param file File to get type of
     * @return File type
     */
    public static String getFileType(File file) {
        try {
            String mimetype = Files.probeContentType(file.toPath());

            if (mimetype != null) {
                if (mimetype.contains("/")) {
                    return mimetype.split("/")[0];
                } else {
                    return mimetype;
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return "";
    }
}
