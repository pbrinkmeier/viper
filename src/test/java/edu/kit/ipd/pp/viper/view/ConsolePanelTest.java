package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;
import edu.kit.ipd.pp.viper.controller.ZoomType;

public class ConsolePanelTest {
    private MainWindow gui;
    private ConsolePanel console;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.console = this.gui.getConsolePanel();
    }
    
    /**
     * Sets and checks the text from the input field.
     */
    @Test
    public void getInputFieldTextTest() {
        double controlNumber = Math.random() * 100;
        this.console.setInputFieldText("test " + controlNumber);
        assertEquals("test " + controlNumber, this.console.getInputFieldText());
    }
    
    /**
     * Clears the input field and checks if it worked.
     */
    @Test
    public void clearInputFieldTest() {
        this.console.setInputFieldText("test");
        this.console.clearInputField();
        assertEquals("", this.console.getInputFieldText());
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
                (this.console.getOutputAreaFontSize() - 10) + "\">test " + controlNumber + "\n" + 
                "</font>    </p>\n" + 
                "  </body>\n" + 
                "</html>";
        
        this.console.clearOutputArea();
        this.console.printLine("test " + controlNumber, LogType.INFO);
        assertEquals(testOutputArea, this.console.getOutputAreaText().trim());
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
        
        this.console.clearOutputArea();
        assertEquals(emptyOutputArea, this.console.getOutputAreaText().trim());
    }
    
    /**
     * Resets the font size of the output area.
     */
    @Test
    public void resetZoomTest() {
        this.console.zoomOutputArea(ZoomType.ZOOM_IN);
        this.console.resetZoom();
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE,
                this.console.getOutputAreaFontSize());
    }
    
    /**
     * Increases the font size of the output area.
     */
    @Test
    public void zoomInOutputAreaTest() {
        this.console.resetZoom();
        this.console.zoomOutputArea(ZoomType.ZOOM_IN);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE + 1,
                this.console.getOutputAreaFontSize());
    }
    
    /**
     * Checks if the font size doesn't increase when it's already max
     */
    @Test
    public void zoomInMaxOutputAreaTest() {
        this.console.setOutputAreaFontSize(40);
        this.console.zoomOutputArea(ZoomType.ZOOM_IN);
        assertEquals(40, this.console.getOutputAreaFontSize());
    }
    
    /**
     * Decreases the font size of the output area.
     */
    @Test
    public void zoomOutOutputAreaTest() {
        this.console.resetZoom();
        this.console.zoomOutputArea(ZoomType.ZOOM_OUT);
        assertEquals(PreferencesManager.DEFAULT_TEXT_SIZE - 1,
                this.console.getOutputAreaFontSize());
    }
    
    /**
     * Checks if the font size doesn't increase when it's already max
     */
    @Test
    public void zoomOutMaxOutputAreaTest() {
        this.console.setOutputAreaFontSize(10);
        this.console.zoomOutputArea(ZoomType.ZOOM_OUT);
        assertEquals(10, this.console.getOutputAreaFontSize());
    }
}