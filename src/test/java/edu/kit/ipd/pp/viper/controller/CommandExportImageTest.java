package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

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
    public void nullFileTest() {
        new CommandExportImage(this.gui.getConsolePanel(), ImageFormat.SVG, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(null)).execute();        
    }
    
    /**
     * Tests the error output of the command.
     */
    @Test
    public void errorOutputTest() {
        final String testPath = "/test/test.pl";
        final IOException exception = new IOException("Test");

        CommandExportImage command = new CommandExportImage(this.gui.getConsolePanel(),
                ImageFormat.PNG, this.gui.getInterpreterManager());
        
        this.gui.setDebugMode(false);
        this.gui.getConsolePanel().clearAll();
        command.printExportError(exception, testPath);
        assertFalse(this.gui.getConsolePanel().getOutputAreaText().isEmpty());

        this.gui.setDebugMode(true);
        this.gui.getConsolePanel().clearAll();
        command.printExportError(exception, testPath);
        assertFalse(this.gui.getConsolePanel().getOutputAreaText().isEmpty());        
    }
}
