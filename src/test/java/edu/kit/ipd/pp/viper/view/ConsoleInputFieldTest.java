package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

public class ConsoleInputFieldTest extends ViewTest {
    
    /**
     * Checks if queries are correctly added to the input fields history.
     */
    @Test
    public void addHistoryTest() {
        this.gui.getConsolePanel().getInputField().clearHistory();
        this.gui.getConsolePanel().getInputField().setText("grandfather(X, Y).");
        //clear calls the private method addHistory()
        this.gui.getConsolePanel().getInputField().clear();
        
        assertEquals("grandfather(X, Y).", this.gui.getConsolePanel().getInputField().getHistory().get(0));
    }
    
    /**
     * Checks if the query isn't added if it is the default hint.
     */
    @Test
    public void addHistoryTextIsHintTest() {
        this.gui.getConsolePanel().getInputField().clearHistory();
        this.gui.getConsolePanel().getInputField().setText(
                LanguageManager.getInstance().getString(LanguageKey.INPUT_HINT));
        //clear calls the private method addHistory()
        this.gui.getConsolePanel().getInputField().clear();
        
        assertEquals(0, this.gui.getConsolePanel().getInputField().getHistory().size());
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
    
    /**
     * Tests if nothing happens when an unknown key has been pressed
     */
    @Test
    public void keyPressedUnknownKeyTest() {
        this.gui.getConsolePanel().clearInputField();
        this.gui.getConsolePanel().getInputField()
            .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED,
                    1, 2, KeyEvent.VK_B, KeyEvent.CHAR_UNDEFINED));
        
        assertEquals("", this.gui.getConsolePanel().getInputField().getText());
        
        double controlNumber = Math.random() * 100;
        
        this.gui.getConsolePanel().setInputFieldText("test " + controlNumber);
        
        this.gui.getConsolePanel().getInputField()
        .keyPressed(new KeyEvent(this.gui.getConsolePanel().getInputField(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_B, KeyEvent.CHAR_UNDEFINED));
    
        assertEquals("test " + controlNumber, this.gui.getConsolePanel().getInputField().getText());
    }
    
    /**
     * Explicitly tests the no more solutions state in switchClickableState()
     */
    @Test
    public void switchClickableStateNoMoreSolutionsTest() {
        //sets the status of buttonSend to false
        this.gui.getConsolePanel().getInputField().switchClickableState(ClickableState.NOT_PARSED_YET);
        
        assertEquals(false, this.gui.getConsolePanel().getInputField().isButtonSendEnabled());
        
        this.gui.getConsolePanel().getInputField().switchClickableState(ClickableState.NO_MORE_SOLUTIONS);
        
        assertEquals(true, this.gui.getConsolePanel().getInputField().isButtonSendEnabled());
    }
    
    /**
     * Explicitly tests the default case in switchClickableState()
     */
    @Test
    public void switchClickableStateDefaultTest() {
        //sets the status of all components to false
        this.gui.getConsolePanel().getInputField().switchClickableState(ClickableState.NOT_PARSED_YET);
        
        //this state is not defined in the switch since it doesn't have any effect on the input field
        this.gui.getConsolePanel().getInputField().switchClickableState(ClickableState.NEXT_SOLUTION_ABORTED);
        
        assertEquals(false, this.gui.getConsolePanel().getInputField().isButtonSendEnabled());
        assertEquals(false, this.gui.getConsolePanel().getInputField().isTextFieldEditable());
        assertEquals(false, this.gui.getConsolePanel().getInputField().isTextFieldEnabled());
        
      //sets the status of all components to true
        this.gui.getConsolePanel().getInputField().switchClickableState(ClickableState.PARSED_PROGRAM);
        
        //this state is not defined in the switch since it doesn't have any effect on the input field
        this.gui.getConsolePanel().getInputField().switchClickableState(ClickableState.NEXT_SOLUTION_ABORTED);
        
        assertEquals(true, this.gui.getConsolePanel().getInputField().isButtonSendEnabled());
        assertEquals(true, this.gui.getConsolePanel().getInputField().isTextFieldEditable());
        assertEquals(true, this.gui.getConsolePanel().getInputField().isTextFieldEnabled());
    }
    
    /**
     * Tests the focusGained function
     */
    @Test
    public void focusGainedTest() {
        this.gui.getConsolePanel().getInputField().setText(
                LanguageManager.getInstance().getString(LanguageKey.INPUT_HINT));
        this.gui.getConsolePanel().getInputField().setTextFieldForeground(Color.CYAN);
        
        assertEquals(Color.CYAN, this.gui.getConsolePanel().getInputField().getTextFieldForeground());
        
        this.gui.getConsolePanel().getInputField().gainFocus();
        
        assertEquals(ColorScheme.CONSOLE_BLACK,
                this.gui.getConsolePanel().getInputField().getTextFieldForeground());
    }
    
    /**
     * Tests the focusGained function if the textField already has the focus
     */
    @Test
    public void focusGainedFalseTest() {
        //the focusGained function changes the text to "" if the hint was previously displayed
        this.gui.getConsolePanel().getInputField().setText("test");
        this.gui.getConsolePanel().getInputField().setTextFieldForeground(Color.CYAN);
        
        this.gui.getConsolePanel().getInputField().gainFocus();
        
        assertEquals(ColorScheme.CONSOLE_BLACK,
                this.gui.getConsolePanel().getInputField().getTextFieldForeground());
        assertEquals("test", this.gui.getConsolePanel().getInputField().getText());
    }
    
    /**
     * Tests the focusLost function
     */
    @Test
    public void focusLostTest() {
        this.gui.getConsolePanel().getInputField().setText("");
        //sets the text field to editable
        this.gui.getConsolePanel().getInputField().switchClickableState(ClickableState.PARSED_PROGRAM);
        this.gui.getConsolePanel().getInputField().setTextFieldForeground(Color.CYAN);
        
        this.gui.getConsolePanel().getInputField().loseFocus();
        
        assertEquals(ColorScheme.PLACEHOLDER_TEXT_COLOR,
                this.gui.getConsolePanel().getInputField().getTextFieldForeground());
        assertEquals(LanguageManager.getInstance().getString(LanguageKey.INPUT_HINT),
                this.gui.getConsolePanel().getInputField().getText());
    }
    
    /**
     * Tests the focusLost function if the textField isn't empty
     */
    @Test
    public void focusLostFalseTest() {
        //the focusLost function only does anything if the text field is empty
        this.gui.getConsolePanel().getInputField().setText("test");
        this.gui.getConsolePanel().getInputField().setTextFieldForeground(Color.CYAN);
        
        this.gui.getConsolePanel().getInputField().loseFocus();
        
        assertEquals(Color.CYAN,
                this.gui.getConsolePanel().getInputField().getTextFieldForeground());
        assertEquals("test", this.gui.getConsolePanel().getInputField().getText());
    }
    
    /**
     * Calls the keyTyped() function, since this function does nothing this is only for coverage
     */
    @Test
    public void keyTypedTest() {
        this.gui.getConsolePanel().getInputField().keyTyped(null);
    }
    
    /**
     * Calls the keyReleased() function, since this function does nothing this is only for coverage
     */
    @Test
    public void keyReleasedTest() {
        this.gui.getConsolePanel().getInputField().keyReleased(null);
    }
}
