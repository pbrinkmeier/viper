package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.EditorPanel;

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
     * Tests the handling of unsaved changes.
     */
    @Test
    public void handlesUnsavedChangesTest() {
        EditorPanel editor = this.gui.getEditorPanel();
        
        editor.setHasChanged(true);
        editor.setSourceText("test");
        final String editorContent = editor.getSourceText();
        
        this.buildCommandNew(new CancelOptionPane()).execute();
        assertTrue(editor.getSourceText().equals(editorContent));
        assertTrue(editor.hasChanged());
        
        this.buildCommandNew(new NoOptionPane()).execute();
        assertTrue(editor.getSourceText().trim().isEmpty());
        assertFalse(editor.hasChanged());        
    }
    
    private CommandNew buildCommandNew(OptionPane pane) {
        return new CommandNew(this.gui.getConsolePanel(), this.gui.getEditorPanel(),
                this.gui.getVisualisationPanel(), this.gui::setTitle, this.gui::switchClickableState,
                this.gui.getCommandSave(), this.gui.getInterpreterManager(), pane);
    }
}
