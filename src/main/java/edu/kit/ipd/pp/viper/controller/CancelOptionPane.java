package edu.kit.ipd.pp.viper.controller;

import javax.swing.JOptionPane;

/**
 * Option Pane that always returns cancel as an answer.
 */
public class CancelOptionPane implements OptionPane {
    @Override
    public int showOptionDialog(String message, String title) {
        return JOptionPane.CANCEL_OPTION;
    }
}
