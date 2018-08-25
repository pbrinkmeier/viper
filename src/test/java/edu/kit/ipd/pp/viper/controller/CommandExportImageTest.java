package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.model.parser.ParseException;

public class CommandExportImageTest extends ControllerTest {
    @Before
    public void setupProgram() {
        final String program = SharedTestConstants.SIMPSONS_FORMATTED;
        final String query = SharedTestConstants.TEST_QUERY;
        try {
            this.gui.getInterpreterManager().parseKnowledgeBase(program);
            this.gui.getInterpreterManager().parseQuery(query);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.gui.getInterpreterManager().nextSolution(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());        
    }

    /**
     * Tests the PNG export functionality of the command.
     */
    @Test
    public void pngWithExtensionTest() {
        CommandExportImage command = new CommandExportImage(this.gui.getConsolePanel(),
                ImageFormat.PNG, this.gui.getInterpreterManager());
        this.gui.getConsolePanel().clearAll();

        File test = new File("test.png");
        command.exportPNG(test);

        assertTrue(test.exists());
        assertTrue(test.getName().equals("test.png"));

        test.delete();
    }

    /**
     * Tests the SVG export functionality of the command.
     */
    @Test
    public void svgWithExtensionTest() {
        CommandExportImage command = new CommandExportImage(this.gui.getConsolePanel(),
                ImageFormat.SVG, this.gui.getInterpreterManager());
        this.gui.getConsolePanel().clearAll();

        File test = new File("test.svg");
        command.exportSVG(test);

        assertTrue(test.exists());
        assertTrue(test.getName().equals("test.svg"));

        test.delete();
    }
}
