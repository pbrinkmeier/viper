package edu.kit.ipd.pp.viper.controller;

/**
 * Enumeration of all supported save types. This contains saving the file
 * normally, which is only possible if the editor has a reference to a file
 * (e.g. after opening it), and saving as a new file.
 */
public enum SaveType {
    /**
     * Save using the reference to a previously saved/opened file
     */
    SAVE,

    /**
     * Save as new file
     */
    SAVE_AS
}