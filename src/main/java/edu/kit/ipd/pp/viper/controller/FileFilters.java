package edu.kit.ipd.pp.viper.controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

/**
 * Collection of file filters used across the project.
 */
public class FileFilters {
    /**
     * File filter used for Prolog files.
     */
    public static final FileFilter PL_FILTER = createFilter(LanguageKey.PROLOG_FILES, "pl");

    /**
     * File filter used for PNG files.
     */
    public static final FileFilter PNG_FILTER = createFilter(LanguageKey.PNG_FILES, "png");

    /**
     * File filter used for SVG files.
     */
    public static final FileFilter SVG_FILTER = createFilter(LanguageKey.SVG_FILES, "svg");

    private static FileFilter createFilter(LanguageKey key, String extension) {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                boolean isDir = file.isDirectory();
                boolean hasValidExtension = FilenameUtils.getExtension(file.getName()).toLowerCase().equals(extension);
                return isDir || hasValidExtension;
            }

            @Override
            public String getDescription() {
                return LanguageManager.getInstance().getString(key);
            }
        };
    }
}
