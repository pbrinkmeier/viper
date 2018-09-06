package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

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
        this.prefMan.setTextSize(PreferencesManager.DEFAULT_TEXT_SIZE);
    }

    /**
     * Tests if the Zoom (the font size) is reset correctly
     */
    @Test
    public void resetZoomTest() {
        this.prefMan.setTextSize(20);
        this.editor = new EditorPanel(gui);
        this.editor.resetZoom();
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE == this.editor.getFontSize());
    }

    /**
     * Tests whether the Font is increased correctly from the default font size.
     */
    @Test
    public void increaseFontTest() {
        this.prefMan.setTextSize(PreferencesManager.DEFAULT_TEXT_SIZE);
        this.editor = new EditorPanel(gui);
        this.editor.zoom(ZoomType.ZOOM_IN);
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.editor.getFontSize());
    }

    /**
     * Tests if the Font is not increased when the max font size has been reached.
     */
    @Test
    public void increaseMaxFontTest() {
        this.prefMan.setTextSize(40);
        this.editor = new EditorPanel(gui);
        this.editor.zoom(ZoomType.ZOOM_IN);
        assertTrue(40 == this.prefMan.getTextSize() && 40 == this.editor.getFontSize());
    }

    /**
     * Tests if the Font is decreased correctly from the default font size.
     */
    @Test
    public void decreaseFontTest() {
        this.prefMan.setTextSize(PreferencesManager.DEFAULT_TEXT_SIZE);
        this.editor = new EditorPanel(gui);
        this.editor.zoom(ZoomType.ZOOM_OUT);
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.editor.getFontSize());
    }

    /**
     * Tests if the Font is not increased when the min font size has been reached.
     */
    @Test
    public void decreaseMinFontTest() {
       this.prefMan.setTextSize(10);
       this.editor = new EditorPanel(gui);
       this.editor.zoom(ZoomType.ZOOM_OUT);
       assertTrue(10 == this.prefMan.getTextSize() && 10 == this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom in via the "ctrl" and "+" keys works
     */
    @Test
    public void keyPressedPlusKeyTest() {
        this.editor = gui.getEditorPanel();
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_PLUS, KeyEvent.CHAR_UNDEFINED));
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom in via the "ctrl" and "numpad +" keys works
     */
    @Test
    public void keyPressedNumPlusKeyTest() {
        this.editor = gui.getEditorPanel();
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_ADD, KeyEvent.CHAR_UNDEFINED));
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom out via the "ctrl" and "-" keys works
     */
    @Test
    public void keyPressedMinusKeyTest() {
        this.editor = gui.getEditorPanel();
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_MINUS, KeyEvent.CHAR_UNDEFINED));
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom out via the "ctrl" and "numpad -" keys works
     */
    @Test
    public void keyPressedNumMinusKeyTest() {
        this.editor = gui.getEditorPanel();
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_SUBTRACT, KeyEvent.CHAR_UNDEFINED));
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom in with the mouse wheel works
     */
    @Test
    public void mousewheelMovedZoomInTest() {
        this.editor = gui.getEditorPanel();
        this.editor.resetZoom();
        this.editor.mouseWheelMoved(new MouseWheelEvent(this.editor.getTextArea(), 1, 1, 2, 1, 1, 1,
                false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, -1));
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE + 1 == this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom out with the mouse wheel works
     */
    @Test
    public void mousewheelMovedZoomOutTest() {
        this.editor = gui.getEditorPanel();
        this.editor.resetZoom();
        this.editor.mouseWheelMoved(new MouseWheelEvent(this.editor.getTextArea(), 1, 1, 2, 1, 1, 1,
                false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, 1));
        assertTrue(PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.prefMan.getTextSize()
                && PreferencesManager.DEFAULT_TEXT_SIZE - 1 == this.editor.getFontSize());
    }
}
