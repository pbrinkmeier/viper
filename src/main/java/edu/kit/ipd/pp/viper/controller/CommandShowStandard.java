package edu.kit.ipd.pp.viper.controller;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * Command for showing a popup with the standard library code used.
 */
public class CommandShowStandard extends Command {
    private boolean isOpened;
    private Font font;

    private JFrame frame;
    private JTextArea textArea;
    
    private InterpreterManager interpreterManager;
    
    /**
     * Initializes a new show standard library popup command.
     * 
     * @param interpreterManager the interpreter manager to fetch the code from
     */
    public CommandShowStandard(InterpreterManager interpreterManager) {
        this.isOpened = false;
        this.interpreterManager = interpreterManager;
        this.font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    }
    
    @Override
    public void execute() {
        if (!this.isOpened) {
            final Dimension dim = new Dimension(400, 300);
            
            this.frame = new JFrame();
            this.frame.setSize(dim);
            this.frame.setMinimumSize(dim);
            this.frame.setTitle(LanguageManager.getInstance().getString(LanguageKey.STANDARD_LIBRARY));
            this.frame.setLocationRelativeTo(null);
            this.frame.setResizable(true);
            this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.frame.setVisible(true);
            
            this.textArea = new JTextArea(this.interpreterManager.getStandardLibraryCode());
            this.textArea.setEditable(false);
            this.textArea.setLineWrap(true);
            this.textArea.setFont(this.font);
            this.frame.add(this.textArea);

            this.isOpened = true;
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    isOpened = false;
                }
            });
        }
        
        this.frame.requestFocus();
        this.frame.toFront();
    }
}
