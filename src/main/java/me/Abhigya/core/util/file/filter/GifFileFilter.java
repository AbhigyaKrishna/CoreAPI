package me.Abhigya.core.util.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for GIF files path names.
 */
public class GifFileFilter implements FileFilter {

    public static final String GIF_EXTENSION = "gif";

    @Override
    public boolean accept(File file) {
        return FileExtensionFilter.of(GIF_EXTENSION).accept(file);
    }

}
