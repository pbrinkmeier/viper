package edu.kit.ipd.pp.viper.controller;

import javax.swing.JOptionPane;

/**
 * Option Pane that always returns no as an answer.
 */
public class NoOptionPane implements OptionPane {
    @Override
    public int showOptionDialog(String message, String title) {
        return JOptionPane.NO_OPTION;
    }
}
