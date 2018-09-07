package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;
import edu.kit.ipd.pp.viper.controller.ZoomType;

public class EditorPanelTest {
    private MainWindow gui;
    private EditorPanel editor;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.editor = this.gui.getEditorPanel();
    }

    /**
     * Tests if the Zoom (the font size) is reset correctly
     */
    @Test
    public void resetZoomTest() {
        this.editor.setFontSize(20);
        this.editor.resetZoom();
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE, editor.getFontSize());
    }

    /**
     * Tests whether the Font is increased correctly from the default font size.
     */
    @Test
    public void increaseFontTest() {
        this.editor.resetZoom();
        this.editor.zoom(ZoomType.ZOOM_IN);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.editor.getFontSize());
    }

    /**
     * Tests if the Font is not increased when the max font size has been reached.
     */
    @Test
    public void increaseMaxFontTest() {
        this.editor.setFontSize(40);
        this.editor.zoom(ZoomType.ZOOM_IN);
        assertEquals(40, this.editor.getFontSize());
    }

    /**
     * Tests if the Font is decreased correctly from the default font size.
     */
    @Test
    public void decreaseFontTest() {
        this.editor.resetZoom();
        this.editor.zoom(ZoomType.ZOOM_OUT);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.editor.getFontSize());
    }

    /**
     * Tests if the Font is not increased when the min font size has been reached.
     */
    @Test
    public void decreaseMinFontTest() {
       this.editor.setFontSize(10);
       this.editor.zoom(ZoomType.ZOOM_OUT);
       assertEquals(10, this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom in via the "ctrl" and "+" keys works
     */
    @Test
    public void keyPressedPlusKeyTest() {
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_PLUS, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom in via the "ctrl" and "numpad +" keys works
     */
    @Test
    public void keyPressedNumPlusKeyTest() {
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_ADD, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom out via the "ctrl" and "-" keys works
     */
    @Test
    public void keyPressedMinusKeyTest() {
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_MINUS, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom out via the "ctrl" and "numpad -" keys works
     */
    @Test
    public void keyPressedNumMinusKeyTest() {
        this.editor.resetZoom();
        this.editor.keyPressed(new KeyEvent(this.editor.getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_SUBTRACT, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom in with the mouse wheel works
     */
    @Test
    public void mousewheelMovedZoomInTest() {
        this.editor.resetZoom();
        this.editor.mouseWheelMoved(new MouseWheelEvent(this.editor.getTextArea(), 1, 1, 2, 1, 1, 1,
                false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, -1));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.editor.getFontSize());
    }
    
    /**
     * Tests if the zoom out with the mouse wheel works
     */
    @Test
    public void mousewheelMovedZoomOutTest() {
        this.editor.resetZoom();
        this.editor.mouseWheelMoved(new MouseWheelEvent(this.editor.getTextArea(), 1, 1, 2, 1, 1, 1,
                false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, 1));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.editor.getFontSize());
    }
}
