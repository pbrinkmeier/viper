package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandNewTest extends ControllerTest {
    /**
     * Tests whether the new command actually clears the editor. This test assumes
     * there are no changes left to be saved to disk.
     */
    @Test
    public void getsClearedTest() {
        this.gui.getEditorPanel().setSourceText("test");
        this.gui.getEditorPanel().setHasChanged(false);
        this.gui.getCommandNew().execute();
        
        assertTrue(this.gui.getEditorPanel().getSourceText().equals(""));
    }
}
