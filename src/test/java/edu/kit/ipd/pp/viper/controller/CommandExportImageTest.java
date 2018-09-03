package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class CommandExportImageTest extends ControllerTest {
    /**
     * Creates a query for export.
     */
    @Before
    public void setupQuery() {
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();
    }
    
    /**
     * Tests exporting the visualisation as a PNG file.
     */
    @Test
    public void pngByDialogTest() {
        File file = new File("test.png");
        new CommandExportImage(this.gui.getConsolePanel(), ImageFormat.PNG, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(file)).execute();
        
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
        
        file.delete();
    }

    /**
     * Tests exporting the visualisation as a SVG file.
     */
    @Test
    public void svgByDialogTest() {
        File file = new File("test.svg");
        new CommandExportImage(this.gui.getConsolePanel(), ImageFormat.SVG, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(file)).execute();
        
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
        
        file.delete();        
    }
    
    /**
     * Tests exporting a null file.
     */
    @Test
    public void testNullFile() {
        new CommandExportImage(this.gui.getConsolePanel(), ImageFormat.SVG, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(null)).execute();        
    }
}
