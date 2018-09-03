package edu.kit.ipd.pp.viper.controller;

import java.io.File;

import org.junit.Test;

public class CommandExitTest extends ControllerTest {
    /**
     * Tests the yes option in the unsaved changes option pane.
     */
    @Test
    public void saveOnExitTest() {
        File file = new File("test.pl");
        CommandSave commandSave = new CommandSave(this.gui.getConsolePanel(), this.gui.getEditorPanel(),
                SaveType.SAVE_AS, this.gui::setTitle, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(file));

        this.gui.getEditorPanel().setHasChanged(true);
        this.buildCommandExit(commandSave, new YesOptionPane()).execute();
        
        file.delete();
    }

    /**
     * Tests the no option in the unsaved changes option pane.
     */
    @Test
    public void doNotSaveChangesTest() {
        this.gui.getEditorPanel().setHasChanged(true);
        this.buildCommandExit(this.gui.getCommandSave(), new NoOptionPane()).execute();        
    }

    /**
     * Tests the cancel option in the unsaved changes option pane.
     */
    @Test
    public void cancelTest() {
        this.gui.getEditorPanel().setHasChanged(true);
        this.buildCommandExit(this.gui.getCommandSave(), new CancelOptionPane()).execute();
    }
    
    /**
     * Tests command execution when there are no unsaved changes.
     */
    @Test
    public void nothingUnsavedTest() {
        this.buildCommandExit(this.gui.getCommandSave(), new CancelOptionPane()).execute();
    }
    
    private CommandExit buildCommandExit(CommandSave save, OptionPane pane) {
        return new CommandExit(this.gui.getEditorPanel(), save,
                this.gui.getInterpreterManager(), this.gui::dispose, pane);
    }
}
