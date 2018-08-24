package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandNewTest {
    /**
     * Tests whether the new command actually clears the editor. This test assumes
     * there are no changes left to be saved to disk.
     */
    @Test
    public void getsClearedTest() {
        MainWindow gui = new MainWindow(false);
        
        gui.getEditorPanel().setSourceText("test");
        gui.getEditorPanel().setHasChanged(false);
        gui.getCommandNew().execute();
        
        assertTrue(gui.getEditorPanel().getSourceText().equals(""));
    }
}
