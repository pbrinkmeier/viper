package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import edu.kit.ipd.pp.viper.controller.PreferencesManager;
import edu.kit.ipd.pp.viper.controller.ZoomType;

public class EditorPanelTest {
    private MainWindow gui;
    private EditorPanel editor;
    private PreferencesManager prefMan;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.prefMan = this.gui.getPreferencesManager();
    }

    /**
     * Tests if the Zoom (the font size) is reset correctly
     */
    @Test
    public void resetZoomTest() {
        this.prefMan.setTextSize(20);
        this.editor = new EditorPanel(gui);
        this.editor.resetZoom();
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE, this.prefMan.getTextSize());
    }

    /**
     * Tests whether the Font is increased correctly from the default font size.
     */
    @Test
    public void increaseFontTest() {
        this.prefMan.setTextSize(PreferencesManager.DEFAULT_TEXT_SIZE);
        this.editor = new EditorPanel(gui);
        this.editor.zoom(ZoomType.ZOOM_IN);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.prefMan.getTextSize());
    }

    /**
     * Tests if the Font is not increased when the max font size has been reached.
     */
    @Test
    public void increaseMaxFontTest() {
        this.prefMan.setTextSize(40);
        this.editor = new EditorPanel(gui);
        this.editor.zoom(ZoomType.ZOOM_IN);
        assertEquals(40, this.prefMan.getTextSize());
    }

    /**
     * Tests if the Font is decreased correctly from the default font size.
     */
    @Test
    public void decreaseFontTest() {
        this.prefMan.setTextSize(PreferencesManager.DEFAULT_TEXT_SIZE);
        this.editor = new EditorPanel(gui);
        this.editor.zoom(ZoomType.ZOOM_OUT);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.prefMan.getTextSize());
    }

    /**
     * Tests if the Font is not increased when the min font size has been reached.
     */
    @Test
    public void decreaseMinFontTest() {
       this.prefMan.setTextSize(10);
       this.editor = new EditorPanel(gui);
       this.editor.zoom(ZoomType.ZOOM_OUT);
       assertEquals(10, this.prefMan.getTextSize());
    }
}
