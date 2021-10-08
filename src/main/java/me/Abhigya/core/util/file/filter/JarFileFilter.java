package me.Abhigya.core.util.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for JAR files path names
 */
public class JarFileFilter implements FileFilter {

    public static final String JAR_EXTENSION = "jar";

    @Override
    public boolean accept( File file ) {
        return FileExtensionFilter.of( JAR_EXTENSION ).accept( file );
    }

}
