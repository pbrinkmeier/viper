package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;
import edu.kit.ipd.pp.viper.controller.ZoomType;

public class EditorPanelTest extends ViewTest {

    /**
     * Tests if the Zoom (the font size) is reset correctly
     */
    @Test
    public void resetZoomTest() {
        this.gui.getEditorPanel().setFontSize(20);
        this.gui.getEditorPanel().resetZoom();
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE, this.gui.getEditorPanel().getFontSize());
    }

    /**
     * Tests whether the Font is increased correctly from the default font size.
     */
    @Test
    public void increaseFontTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel().zoom(ZoomType.ZOOM_IN);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.gui.getEditorPanel().getFontSize());
    }

    /**
     * Tests if the Font is not increased when the max font size has been reached.
     */
    @Test
    public void increaseMaxFontTest() {
        this.gui.getEditorPanel().setFontSize(40);
        this.gui.getEditorPanel().zoom(ZoomType.ZOOM_IN);
        assertEquals(40, this.gui.getEditorPanel().getFontSize());
    }

    /**
     * Tests if the Font is decreased correctly from the default font size.
     */
    @Test
    public void decreaseFontTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel().zoom(ZoomType.ZOOM_OUT);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.gui.getEditorPanel().getFontSize());
    }

    /**
     * Tests if the Font is not increased when the min font size has been reached.
     */
    @Test
    public void decreaseMinFontTest() {
       this.gui.getEditorPanel().setFontSize(10);
       this.gui.getEditorPanel().zoom(ZoomType.ZOOM_OUT);
       assertEquals(10, this.gui.getEditorPanel().getFontSize());
    }
    
    /**
     * Tests if the zoom in via the "ctrl" and "+" keys works
     */
    @Test
    public void keyPressedPlusKeyTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel().keyPressed(new KeyEvent(this.gui.getEditorPanel().getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_PLUS, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.gui.getEditorPanel().getFontSize());
    }
    
    /**
     * Tests if the zoom in via the "ctrl" and "numpad +" keys works
     */
    @Test
    public void keyPressedNumPlusKeyTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel().keyPressed(new KeyEvent(this.gui.getEditorPanel().getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_ADD, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.gui.getEditorPanel().getFontSize());
    }
    
    /**
     * Tests if the zoom out via the "ctrl" and "-" keys works
     */
    @Test
    public void keyPressedMinusKeyTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel().keyPressed(new KeyEvent(this.gui.getEditorPanel().getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_MINUS, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.gui.getEditorPanel().getFontSize());
    }
    
    /**
     * Tests if the zoom out via the "ctrl" and "numpad -" keys works
     */
    @Test
    public void keyPressedNumMinusKeyTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel().keyPressed(new KeyEvent(this.gui.getEditorPanel().getTextArea(), KeyEvent.KEY_PRESSED,
                1, 2, KeyEvent.VK_SUBTRACT, KeyEvent.CHAR_UNDEFINED));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.gui.getEditorPanel().getFontSize());
    }
    
    /**
     * Tests if the zoom in with the mouse wheel works
     */
    @Test
    public void mousewheelMovedZoomInTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel()
            .mouseWheelMoved(new MouseWheelEvent(this.gui.getEditorPanel().getTextArea(), 1, 1, 2, 1, 1, 1,
                false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, -1));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1, this.gui.getEditorPanel().getFontSize());
    }
    
    /**
     * Tests if the zoom out with the mouse wheel works
     */
    @Test
    public void mousewheelMovedZoomOutTest() {
        this.gui.getEditorPanel().resetZoom();
        this.gui.getEditorPanel()
            .mouseWheelMoved(new MouseWheelEvent(this.gui.getEditorPanel().getTextArea(), 1, 1, 2, 1, 1, 1,
                false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, 1));
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1, this.gui.getEditorPanel().getFontSize());
    }
}
