package edu.kit.ipd.pp.viper.view;

import javax.swing.KeyStroke;

public enum KeyboardShortcut {
    /**
     * Keyboard shortcut for the new command
     */
    NEW("control N"),

    /**
     * Keyboard shortcut for the open command
     */
    OPEN("control O"),
    
    /**
     * Keyboard shortcut for the save command
     */
    SAVE("control S"),
    
    /**
     * Keyboard shortcut for the save as command
     */
    SAVE_AS("control shift S"),
    
    /**
     * Keyboard shortcut for the exit command
     */
    EXIT("control Q"),
    
    /**
     * Keyboard shortcut for the parse command
     */
    PARSE("control P"),
    
    /**
     * Keyboard shortcut for the format command
     */
    FORMAT("control F"),
    
    /**
     * Keyboard shortcut for resetting the visualisation zoom
     */
    RESET_VISUALISATION_ZOOM("control R"),
    
    /**
     * Keyboard shortcut for resetting the text zoom
     */
    RESET_TEXT_ZOOM("control T");    
    
    private KeyStroke stroke;
    private KeyboardShortcut(String stringRep) {
        this.stroke = KeyStroke.getKeyStroke(stringRep);
    }
    
    /**
     * Getter-Method for the key stroke of the shortcut
     * 
     * @return the keystroke of the shortcut
     */
    public KeyStroke getKeyStroke() {
        return this.stroke;
    }
}
