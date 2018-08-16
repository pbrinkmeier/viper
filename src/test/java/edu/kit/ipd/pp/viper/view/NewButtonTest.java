package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertTrue;

import org.assertj.swing.fixture.FrameFixture;
import org.junit.Before;
import org.junit.Test;

public class NewButtonTest {
    private MainWindow gui;
    private EditorPanel editor;
    
    private FrameFixture fixture;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.editor = this.gui.getEditorPanel();

        this.fixture = new FrameFixture(this.gui);
        this.fixture.show();
    }
    
    @Test
    public void getsCleared() {
        this.editor.setSourceText("test");
        this.editor.setHasChanged(false);
        this.fixture.button(GUIComponentID.BUTTON_NEW.toString()).click();
        assertTrue(this.editor.getSourceText().trim().isEmpty());
    }
}
