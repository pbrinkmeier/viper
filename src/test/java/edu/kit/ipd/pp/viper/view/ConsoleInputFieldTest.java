package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;

import org.junit.Test;

public class ConsoleInputFieldTest extends ViewTest {
    
    /**
     * Checks if queries are correctly added to the input fields history.
     */
    @Test
    public void addHistoryTest() {
        this.gui.getConsolePanel().getInputField().setText("grandfather(X, Y).");
        //clear calls the private method addHistory()
        this.gui.getConsolePanel().getInputField().clear();
        
        assertEquals("grandfather(X, Y).", this.gui.getConsolePanel().getInputField().getHistory().get(0));
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when jumping
     * back.
     */
    @Test
    public void keyPressedUpTest() {
        this.gui.getConsolePanel().getInputField().clearHistory();
        this.gui.getConsolePanel().getInputField().setText("grandfather(X, Y).");
        this.gui.getConsolePanel().getInputField().clear();
        
        this.gui.getConsolePanel().getInputField()
            .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED, 
                    1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        assertEquals("grandfather(X, Y).", this.gui.getConsolePanel().getInputField().getText());
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when the user has
     * reached the last entry and keeps pressing up.
     */
    @Test
    public void keyPressedUpLastTest() {
        this.gui.getConsolePanel().getInputField().clearHistory();
        this.gui.getConsolePanel().getInputField().setText("grandfather(X, Y).");
        this.gui.getConsolePanel().getInputField().clear();
        this.gui.getConsolePanel().getInputField().setText("mother(X, Y).");
        this.gui.getConsolePanel().getInputField().clear();
        
        for (int i = 0; i < 3; i++) {
            this.gui.getConsolePanel().getInputField()
                .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED,
                    1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        }
        
        assertEquals("grandfather(X, Y).", this.gui.getConsolePanel().getInputField().getText());
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when jumping
     * from a recent entry to the beginning.
     */
    @Test
    public void keyPressedDownTest() {
        this.gui.getConsolePanel().getInputField().clearHistory();
        this.gui.getConsolePanel().getInputField().setText("grandfather(X, Y).");
        this.gui.getConsolePanel().getInputField().clear();
        
        this.gui.getConsolePanel().getInputField()
            .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        this.gui.getConsolePanel().getInputField()
            .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
        assertEquals("", this.gui.getConsolePanel().getInputField().getText());
    }
    
    /**
     * Checks if the history of recently entered queries is accessed accordingly when the user has
     * reached the empty input field for a new query and keeps pressing down.
     */
    @Test
    public void keyPressedDownLastTest() {
        this.gui.getConsolePanel().getInputField().clearHistory();
        this.gui.getConsolePanel().getInputField().setText("grandfather(X, Y).");
        this.gui.getConsolePanel().getInputField().clear();
        this.gui.getConsolePanel().getInputField().setText("mother(X, Y).");
        this.gui.getConsolePanel().getInputField().clear();
        
        for (int i = 0; i < 3; i++) {
            this.gui.getConsolePanel().getInputField()
                .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED,
                    1, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        }
        for (int i = 0; i < 4; i++) {
            this.gui.getConsolePanel().getInputField()
                .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED,
                    1, 2, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
        }
        
        assertEquals("", this.gui.getConsolePanel().getInputField().getText());
    }
}
