package edu.kit.ipd.pp.viper.controller;

import javax.swing.JOptionPane;

/**
 * Option Pane that always returns yes as an answer.
 */
public class YesOptionPane implements OptionPane {
    @Override
    public int showOptionDialog(String message, String title) {
        return JOptionPane.YES_OPTION;
    }
}
