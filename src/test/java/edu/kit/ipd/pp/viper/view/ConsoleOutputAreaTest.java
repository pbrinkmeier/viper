package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;

public class ConsoleOutputAreaTest {
    private MainWindow gui;
    private ConsoleOutputArea outputArea;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.outputArea = this.gui.getConsolePanel().getOutputArea();
    }
    
    /**
     * Resets the font size to the default.
     */
    @Test
    public void resetFontTest() {
        this.outputArea.setFontSize(20);
        this.outputArea.resetFont();
        
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE,
                this.outputArea.getFontSize());
    }
    
    /**
     * Increases the font size.
     */
    @Test
    public void increaseFontTest() {
        this.outputArea.resetFont();
        this.outputArea.increaseFont();
        
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1,
                this.outputArea.getFontSize());
    }
    
    /**
     * Decrease the font size.
     */
    @Test
    public void decreaseFontTest() {
        this.outputArea.resetFont();
        this.outputArea.decreaseFont();
        
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1,
                this.outputArea.getFontSize());
    }
}
