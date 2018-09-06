package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;

public class ConsoleInputFieldTest {
    private MainWindow gui;
    private ConsoleInputField inputField;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.inputField = this.gui.getConsolePanel().getInputField();
    }
    
    /**
     * Checks if queries are correctly added to the input fields history.
     */
    @Test
    public void addHistoryTest() {
        this.inputField.setText("grandfather(X, Y).");
        //clear calls the private method addHistory()
        this.inputField.clear();
        
        assertEquals("grandfather(X, Y).", this.inputField.getHistory().get(0));
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when jumping
     * back.
     */
    @Test
    public void keyPressedUpTest() {
        this.inputField.clearHistory();
        this.inputField.setText("grandfather(X, Y).");
        this.inputField.clear();
        
        this.inputField.keyPressed(new KeyEvent(this.inputField, KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        assertEquals("grandfather(X, Y).", this.inputField.getText());
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when the user has
     * reached the last entry and keeps pressing up.
     */
    @Test
    public void keyPressedUpLastTest() {
        this.inputField.clearHistory();
        this.inputField.setText("grandfather(X, Y).");
        this.inputField.clear();
        this.inputField.setText("mother(X, Y).");
        this.inputField.clear();
        
        for(int i = 0; i < 3; i++) {
            this.inputField.keyPressed(new KeyEvent(this.inputField, KeyEvent.KEY_PRESSED,
                    1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        }
        
        assertEquals("grandfather(X, Y).", this.inputField.getText());
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when jumping
     * from a recent entry to the beginning.
     */
    @Test
    public void keyPressedDownTest() {
        this.inputField.clearHistory();
        this.inputField.setText("grandfather(X, Y).");
        this.inputField.clear();
        
        this.inputField.keyPressed(new KeyEvent(this.inputField, KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        this.inputField.keyPressed(new KeyEvent(this.inputField, KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
        assertEquals("", this.inputField.getText());
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when the user has
     * reached the empty input field for a new query and keeps pressing down.
     */
    @Test
    public void keyPressedDownLastTest() {
        this.inputField.clearHistory();
        this.inputField.setText("grandfather(X, Y).");
        this.inputField.clear();
        this.inputField.setText("mother(X, Y).");
        this.inputField.clear();
        
        for(int i = 0; i < 3; i++) {
            this.inputField.keyPressed(new KeyEvent(this.inputField, KeyEvent.KEY_PRESSED,
                    1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        }
        for(int i = 0; i < 4; i++) {
            this.inputField.keyPressed(new KeyEvent(this.inputField, KeyEvent.KEY_PRESSED,
                    1, 2, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
        }
        
        assertEquals("", this.inputField.getText());
    }
}
