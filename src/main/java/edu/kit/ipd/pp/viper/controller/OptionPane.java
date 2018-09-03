package edu.kit.ipd.pp.viper.controller;

/**
 * Abstract option pane. This can be implemented to show the default
 * Java JOptionPane implementation or be hardcoded to just return a
 * specific value for testing purposes.
 */
public interface OptionPane {
    /**
     * Returns the result of showing an option dialog. This might be hardcoded
     * to be yes, no or cancel for testing purposes.
     * 
     * @param message The message to be shown
     * @param title The title of the dialog
     * @return The result of the dialog
     */
    int showOptionDialog(String message, String title);
}
