package edu.kit.ipd.pp.viper.controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Abstract file chooser interface that allows for using the
 * default Java JFileChooser implementation or a hardcoded one that
 * always returns a specific file.
 */
public interface FileChooser {
    /**
     * Depending on the implementation, shows a real Save dialog or returns
     * a predefined file.
     * 
     * @param filter The file filter to be applied if there is an actual file chooser
     * @return the selected file or null if there is none
     */
    File showSaveDialog(FileFilter filter);

    /**
     * Depending on the implementation, shows a real File Open dialog or returns
     * a predefined file.
     * 
     * @param filter The file filter to be applied if there is an actual file chooser
     * @return the selected file or null if there is none
     */
    File showOpenDialog(FileFilter filter);
}
