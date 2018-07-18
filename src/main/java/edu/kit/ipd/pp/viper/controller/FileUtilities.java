package edu.kit.ipd.pp.viper.controller;

import java.io.File;

/**
 * Miscellaneous utility functions for working with files.
 */
public class FileUtilities {
    /**
     * Optional concatenation of the file extension routine. This should only be
     * called internally, but it's public for testing purposes.
     * 
     * @param f File that might miss the proper extension
     * @param extension the extension to be checked for
     * @return the file with the proper extension
     */
    public static File checkForMissingExtension(File f, String extension) {
        File file = f;
        String filePath = file.getAbsolutePath();
        if (!filePath.endsWith(extension)) {
            file = new File(filePath + extension);
        }
        return file;
    }
}
