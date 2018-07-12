package edu.kit.ipd.pp.viper.controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

public class FileFilters {
    /**
     * File filter used for Prolog files.
     */
    public static final FileFilter PL_FILTER = new FileFilter() {
        @Override
        public String getDescription() {
            return LanguageManager.getInstance().getString(LanguageKey.PROLOG_FILES);
        }

        @Override
        public boolean accept(File f) {
            return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("pl") || f.isDirectory();
        }
    };
    
    /**
     * File filter used for TikZ files.
     */
    public static final FileFilter TIKZ_FILTER = new FileFilter() {
        @Override
        public String getDescription() {
            return LanguageManager.getInstance().getString(LanguageKey.TIKZ_FILES);
        }

        @Override
        public boolean accept(File f) {
            return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("tikz") || f.isDirectory();
        }
    };
    
    /**
     * File filter used for PNG files.
     */
    public static final FileFilter PNG_FILTER = new FileFilter() {
        @Override
        public String getDescription() {
            return LanguageManager.getInstance().getString(LanguageKey.PNG_FILES);
        }

        @Override
        public boolean accept(File f) {
            return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("png") || f.isDirectory();
        }
    };
    
    /**
     * File filter used for SVG files.
     */
    public static final FileFilter SVG_FILTER = new FileFilter() {
        @Override
        public String getDescription() {
            return LanguageManager.getInstance().getString(LanguageKey.SVG_FILES);
        }

        @Override
        public boolean accept(File f) {
            return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("svg") || f.isDirectory();
        }
    };
}
