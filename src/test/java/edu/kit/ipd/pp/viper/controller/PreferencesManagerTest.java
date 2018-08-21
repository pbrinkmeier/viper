package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsoleOutputArea;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class PreferencesManagerTest {
    /**
     * Tests setting and retrieving different values from
     * the preferences manager.
     */
    @Test
    public void setTestingAndRetrieval() {
        MainWindow gui = new MainWindow(false);
        PreferencesManager prefman = gui.getPreferencesManager();
        
        final int size = 42;
        prefman.setConsoleTextSize(size);
        assertTrue(prefman.getConsoleTextSize() == size);
        prefman.setEditorTextSize(size);
        assertTrue(prefman.getEditorTextSize() == size);
        
        prefman.clearAllProperties();
        assertTrue(prefman.getEditorTextSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(prefman.getConsoleTextSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
    }
    
    /**
     * Tests setting the properties reference to null.
     */
    @Test
    public void testNullifiedProperties() {
        MainWindow gui = new MainWindow(false);
        PreferencesManager prefman = gui.getPreferencesManager();
        
        prefman.setPropertiesToNull();
        assertTrue(prefman.isStandardLibEnabled());
        assertTrue(prefman.getLanguage().equals(Locale.GERMAN));
    }
}
