package edu.kit.ipd.pp.viper.controller;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import edu.kit.ipd.pp.viper.view.GUIComponentID;

/**
 * Command for showing a popup with the standard library code used.
 */
public class CommandShowStandard extends Command {
    /**
     * The dimensions of the popup
     */
    public static final Dimension DIMENSION = new Dimension(400, 300);
    
    private boolean isOpened;
    private Font font;

    private JFrame frame;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    
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
            this.frame = new JFrame();
            this.frame.setName(GUIComponentID.FRAME_SHOW_STD.toString());
            this.frame.setSize(CommandShowStandard.DIMENSION);
            this.frame.setMinimumSize(CommandShowStandard.DIMENSION);
            this.frame.setTitle(LanguageManager.getInstance().getString(LanguageKey.STANDARD_LIBRARY));
            this.frame.setLocationRelativeTo(null);
            this.frame.setResizable(true);
            this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.frame.setVisible(true);
            
            this.textArea = new JTextArea(this.interpreterManager.getStandardLibraryCode());
            this.textArea.setName(GUIComponentID.FRAME_SHOW_STD_TEXTAREA.toString());
            this.textArea.setEditable(false);
            this.textArea.setLineWrap(false);
            this.textArea.setFont(this.font);
            this.frame.add(this.textArea);
            
            this.scrollPane = new JScrollPane(this.textArea,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.frame.add(this.scrollPane);

            this.isOpened = true;
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    CommandShowStandard.this.isOpened = false;
                }
            });
        }
        
        this.frame.requestFocus();
        this.frame.toFront();
    }
}
