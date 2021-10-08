package me.Abhigya.core.util.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for directory files path names.
 */
public class FileDirectoryFilter implements FileFilter {

    @Override
    public boolean accept( File file ) {
        return file.isDirectory( );
    }

}
