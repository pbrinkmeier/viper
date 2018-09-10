package edu.kit.ipd.pp.viper.view;

import org.junit.Test;

public class CheckBoxMenuItemTest extends ViewTest {
    
    /**
     * Calls the actionPerformed function of a menu item to check that
     * there are no exceptions.
     */
    @Test
    public void actionPerformedTest() {
        this.gui.getMainWindowMenuBar().getItemToggleSTD().actionPerformed(null);
    }
}
