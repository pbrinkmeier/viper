package edu.kit.ipd.pp.viper.view;

import org.junit.Test;

public class MenuBarTest extends ViewTest {
    /**
     * Tests the addSettingsMenu function of the MenuBar if the stdlib is disabled
     */
    @Test
    public void disabledStdLibTest() {
        this.gui.getPreferencesManager().setStandardLibEnabled(false);
        this.destroyGUI();
        
        this.buildGUI();
    }
}
