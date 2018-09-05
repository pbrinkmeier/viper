package edu.kit.ipd.pp.viper.controller;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import edu.kit.ipd.pp.viper.view.GUIComponentID;

/**
 * Command for showing a popup with the standard library code used.
 */
public class CommandShowStandard extends Command implements WindowListener {
    /**
     * The dimensions of the popup
     */
    public static final Dimension DIMENSION = new Dimension(400, 300);
    
    private boolean isOpened;
    private Font font;

    private JFrame frame;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    
    private InterpreterManager manager;
    
    /**
     * Initializes a new show standard library popup command.
     * 
     * @param manager the interpreter manager to fetch the code from
     */
    public CommandShowStandard(InterpreterManager manager) {
        super();

        this.isOpened = false;
        this.manager = manager;
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
            
            this.textArea = new JTextArea(this.manager.getStandardLibraryCode());
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
            this.frame.addWindowListener(this);
        }
        
        this.frame.requestFocus();
        this.frame.toFront();
    }

    /**
     * Returns whether the popup is opened
     * 
     * @return boolean value describing whether the popup is opened
     */
    public boolean isOpened() {
        return this.isOpened;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        return;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.isOpened = false;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        return;
    }

    @Override
    public void windowIconified(WindowEvent e) {
        return;
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        return;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        return;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        return;
    }
}
