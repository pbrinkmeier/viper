package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;

public class ConsoleOutputAreaTest extends ViewTest {
    
    /**
     * Resets the font size to the default.
     */
    @Test
    public void resetFontTest() {
        this.gui.getConsolePanel().getOutputArea().setFontSize(20);
        this.gui.getConsolePanel().getOutputArea().resetFont();
        
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE,
                this.gui.getConsolePanel().getOutputArea().getFontSize());
    }
    
    /**
     * Increases the font size.
     */
    @Test
    public void increaseFontTest() {
        this.gui.getConsolePanel().getOutputArea().resetFont();
        this.gui.getConsolePanel().getOutputArea().increaseFont();
        
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1,
                this.gui.getConsolePanel().getOutputArea().getFontSize());
    }
    
    /**
     * Decrease the font size.
     */
    @Test
    public void decreaseFontTest() {
        this.gui.getConsolePanel().getOutputArea().resetFont();
        this.gui.getConsolePanel().getOutputArea().decreaseFont();
        
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1,
                this.gui.getConsolePanel().getOutputArea().getFontSize());
    }
}
