package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

public class PreferencesManagerTest extends ControllerTest {
    /**
     * Tests setting and retrieving different values from
     * the preferences manager.
     */
    @Test
    public void settingAndRetrievalTest() {
        PreferencesManager prefman = this.gui.getPreferencesManager();
        
        final int size = 42;
        prefman.setTextSize(size);
        assertTrue(prefman.getTextSize() == size);
        
        prefman.clearAllProperties();
        assertTrue(prefman.getTextSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
    }
    
    /**
     * Tests setting the properties reference to null.
     */
    @Test
    public void nullifiedPropertiesTest() {
        PreferencesManager prefman = this.gui.getPreferencesManager();
        
        prefman.setPropertiesToNull();
        assertTrue(prefman.isStandardLibEnabled());
        assertTrue(prefman.getLanguage().equals(Locale.GERMAN));
    }
}
