package me.Abhigya.core.util.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for ZIP files path names.
 */
public class RarFileFilter implements FileFilter {

    public static final String RAR_EXTENSION = "rar";

    @Override
    public boolean accept( File file ) {
        return FileExtensionFilter.of( RAR_EXTENSION ).accept( file );
    }

}
