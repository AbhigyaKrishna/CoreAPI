package me.Abhigya.core.util.file;

import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for dealing with file names.
 */
public class FilenameUtils extends org.apache.commons.io.FilenameUtils {

    /**
     * Gets the name of the provided file excluding its extension.
     * <p>
     *
     * @param file File to get.
     * @return Name of the file excluding its extension.
     */
    public static String getBaseName(File file) {
        return getBaseName(file.getName());
    }

    /**
     * Gets the names of all the elements within desired {@code directory},
     * including the files within the sub-directories of the desired directory.
     * <p>
     *
     * @param directory Desired directory.
     * @return Names of all the elements within the directory and its
     * sub-directories.
     */
    public static Set<String> getElementNames(File directory) {
        Validate.isTrue(directory.isDirectory(), "the provided file is not a valid directory!");

        final Set<String> elements = new HashSet<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                elements.addAll(getElementNames(file)); // recursive.
            } else {
                elements.add(getBaseName(file.getName()));
            }
        }
        return elements;
    }

}
