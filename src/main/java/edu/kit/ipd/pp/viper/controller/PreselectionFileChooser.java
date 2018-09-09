package edu.kit.ipd.pp.viper.controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * File chooser implementation that always returns a predefined file.
 */
public class PreselectionFileChooser implements FileChooser {
    private File preselected;
    
    /**
     * Initializes a new preselection file chooser with the passed file.
     * 
     * @param file The file to be returned on request
     */
    public PreselectionFileChooser(File file) {
        this.preselected = file;
    }
    
    @Override
    public File showSaveDialog(FileFilter filter) {
        return this.preselected;
    }

    @Override
    public File showOpenDialog(FileFilter filter) {
        return this.preselected;
    }
}
