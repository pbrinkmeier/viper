package edu.kit.ipd.pp.viper.controller;

import org.junit.Test;

public class CommandExitTest extends ControllerTest {
    /**
     * Tests the cancel option in the unsaved changes option pane.
     */
    @Test
    public void cancelTest() {
        this.gui.getEditorPanel().setHasChanged(true);
        this.buildCommandExit(new CancelOptionPane()).execute();
    }

    /**
     * Tests the no option in the unsaved changes option pane.
     */
    @Test
    public void doNotSaveChangesTest() {
        this.gui.getEditorPanel().setHasChanged(true);
        this.buildCommandExit(new NoOptionPane()).execute();        
    }
    
    /**
     * Tests command execution when there are no unsaved changes.
     */
    @Test
    public void nothingUnsavedTest() {
        this.buildCommandExit(new CancelOptionPane()).execute();
    }
    
    private CommandExit buildCommandExit(OptionPane pane) {
        return new CommandExit(this.gui.getEditorPanel(), this.gui.getCommandSave(),
                this.gui.getInterpreterManager(), this.gui::dispose, pane);
    }
}
