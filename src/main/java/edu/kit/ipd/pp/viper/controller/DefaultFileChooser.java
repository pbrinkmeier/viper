package edu.kit.ipd.pp.viper.controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * File chooser implementation that delegates to the proper Java
 * JFileChooser implementation.
 */
public class DefaultFileChooser implements FileChooser {
    @Override
    public File showSaveDialog(FileFilter filter) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }
}
