package edu.kit.ipd.pp.viper.controller;

import javax.swing.JOptionPane;

/**
 * Option Pane that always actually uses the Java option pane.
 */
public class DefaultOptionPane implements OptionPane {
    @Override
    public int showOptionDialog(String message, String title) {
        LanguageManager langman = LanguageManager.getInstance();
        Object options[] = {
                langman.getString(LanguageKey.DIALOG_YES),
                langman.getString(LanguageKey.DIALOG_NO),
                langman.getString(LanguageKey.DIALOG_CANCEL)
        };
        return JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
    }
}
