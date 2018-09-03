package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

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
    
    /**
     * Tests the handling of unsaved changes by saving.
     */
    @Test
    public void saveOnExitTest() {
        File file = new File("test.pl");
        
        this.gui.getEditorPanel().setHasChanged(true);
        this.gui.getEditorPanel().setSourceText("test");
        CommandSave save = new CommandSave(this.gui.getConsolePanel(), this.gui.getEditorPanel(),
                SaveType.SAVE_AS, this.gui::setTitle, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(file));
        this.buildCommandNew(save, new YesOptionPane()).execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().trim().isEmpty());
        assertFalse(this.gui.getEditorPanel().hasChanged());
        assertTrue(file.length() > 0);
        
        file.delete();
    }

    /**
     * Tests the handling of unsaved changes by not saving.
     */
    @Test
    public void doNotSaveOnExitTest() {
        this.gui.getEditorPanel().setHasChanged(true);
        this.gui.getEditorPanel().setSourceText("test");
        this.buildCommandNew(this.gui.getCommandSave(), new NoOptionPane()).execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().trim().isEmpty());
        assertFalse(this.gui.getEditorPanel().hasChanged());
    }
    
    /**
     * Tests the handling of unsaved changes by canceling.
     */
    @Test
    public void cancelSaveOnExitTest() {
        this.gui.getEditorPanel().setHasChanged(true);
        this.gui.getEditorPanel().setSourceText("test");
        final String editorContent = this.gui.getEditorPanel().getSourceText();
        
        this.buildCommandNew(this.gui.getCommandSave(), new CancelOptionPane()).execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().equals(editorContent));
        assertTrue(this.gui.getEditorPanel().hasChanged());
    }
    
    private CommandNew buildCommandNew(CommandSave save, OptionPane pane) {
        return new CommandNew(this.gui.getConsolePanel(), this.gui.getEditorPanel(),
                this.gui.getVisualisationPanel(), this.gui::setTitle, this.gui::switchClickableState,
                save, this.gui.getInterpreterManager(), pane);
    }
}
