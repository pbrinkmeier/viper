package edu.kit.ipd.pp.viper.view;

import org.junit.Test;

public class ToolBarButtonTest extends ViewTest {
    
    /**
     * Only tests that there are no exceptions when calling the
     * actionPerformed function of a toolbar button
     */
    @Test
    public void actionPerformedTest() {
        this.gui.getToolBar().getButtonNew().actionPerformed(null);
    }
}
