package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;
import edu.kit.ipd.pp.viper.controller.ZoomType;

public class ConsolePanelTest extends ViewTest{
    
    /**
     * Sets and checks the text from the input field.
     */
    @Test
    public void getInputFieldTextTest() {
        double controlNumber = Math.random() * 100;
        this.gui.getConsolePanel().setInputFieldText("test " + controlNumber);
        assertEquals("test " + controlNumber, this.gui.getConsolePanel().getInputFieldText());
    }
    
    /**
     * Clears the input field and checks if it worked.
     */
    @Test
    public void clearInputFieldTest() {
        this.gui.getConsolePanel().setInputFieldText("test");
        this.gui.getConsolePanel().clearInputField();
        assertEquals("", this.gui.getConsolePanel().getInputFieldText());
    }
    
    /**
     * Sets and checks the text from the output area.
     */
    @Test
    public void getOutputAreaTextTest() {
        double controlNumber = Math.random() * 100;
        String testOutputArea = "<html>\n" + 
                "  <head>\n" + 
                "\n" + 
                "  </head>\n" + 
                "  <body>\n" + 
                "    <p style=\"margin-top: 0\">\n" + 
                "      <font color=\"#000000\" face=\"monospaced\" size=\"" + 
                (this.gui.getConsolePanel().getOutputAreaFontSize() - 10) + "\">test " + controlNumber + "\n" + 
                "</font>    </p>\n" + 
                "  </body>\n" + 
                "</html>";
        
        this.gui.getConsolePanel().clearOutputArea();
        this.gui.getConsolePanel().printLine("test " + controlNumber, LogType.INFO);
        assertEquals(testOutputArea, this.gui.getConsolePanel().getOutputAreaText().trim());
    }
    
    /**
     * Clears the output area and checks if it worked.
     */
    @Test
    public void clearOutputAreaTest() {
        String emptyOutputArea = "<html>\n" + 
                "  <head>\n" + 
                "\n" + 
                "  </head>\n" + 
                "  <body>\n" + 
                "    <p style=\"margin-top: 0\">\n" + 
                "      \n" + 
                "    </p>\n" + 
                "  </body>\n" + 
                "</html>";
        
        this.gui.getConsolePanel().clearOutputArea();
        assertEquals(emptyOutputArea, this.gui.getConsolePanel().getOutputAreaText().trim());
    }
    
    /**
     * Resets the font size of the output area.
     */
    @Test
    public void resetZoomTest() {
        this.gui.getConsolePanel().zoomOutputArea(ZoomType.ZOOM_IN);
        this.gui.getConsolePanel().resetZoom();
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE,
                this.gui.getConsolePanel().getOutputAreaFontSize());
    }
    
    /**
     * Increases the font size of the output area.
     */
    @Test
    public void zoomInOutputAreaTest() {
        this.gui.getConsolePanel().resetZoom();
        this.gui.getConsolePanel().zoomOutputArea(ZoomType.ZOOM_IN);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1,
                this.gui.getConsolePanel().getOutputAreaFontSize());
    }
    
    /**
     * Checks if the font size doesn't increase when it's already max
     */
    @Test
    public void zoomInMaxOutputAreaTest() {
        this.gui.getConsolePanel().setOutputAreaFontSize(40);
        this.gui.getConsolePanel().zoomOutputArea(ZoomType.ZOOM_IN);
        assertEquals(40, this.gui.getConsolePanel().getOutputAreaFontSize());
    }
    
    /**
     * Decreases the font size of the output area.
     */
    @Test
    public void zoomOutOutputAreaTest() {
        this.gui.getConsolePanel().resetZoom();
        this.gui.getConsolePanel().zoomOutputArea(ZoomType.ZOOM_OUT);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1,
                this.gui.getConsolePanel().getOutputAreaFontSize());
    }
    
    /**
     * Checks if the font size doesn't increase when it's already max
     */
    @Test
    public void zoomOutMaxOutputAreaTest() {
        this.gui.getConsolePanel().setOutputAreaFontSize(10);
        this.gui.getConsolePanel().zoomOutputArea(ZoomType.ZOOM_OUT);
        assertEquals(10, this.gui.getConsolePanel().getOutputAreaFontSize());
    }
}