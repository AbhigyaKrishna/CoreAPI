package me.Abhigya.core.util.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for ZIP files path names.
 */
public class ZipFileFilter implements FileFilter {

    public static final String ZIP_EXTENSION = "zip";

    @Override
    public boolean accept( File file ) {
        return FileExtensionFilter.of( ZIP_EXTENSION ).accept( file );
    }

}
