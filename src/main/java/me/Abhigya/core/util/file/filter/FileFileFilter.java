package me.Abhigya.core.util.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for file files path names.
 */
public class FileFileFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isFile();
    }

}
