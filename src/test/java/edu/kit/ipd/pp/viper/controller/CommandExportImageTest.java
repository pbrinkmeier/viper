package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandExportImageTest {
    private MainWindow buildGUI() {
        MainWindow gui = new MainWindow(false);

        final String program = SharedTestConstants.SIMPSONS_FORMATTED;
        final String query = SharedTestConstants.TEST_QUERY;
        try {
            gui.getInterpreterManager().parseKnowledgeBase(program);
            gui.getInterpreterManager().parseQuery(query);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gui.getInterpreterManager().nextSolution(gui.getConsolePanel(), gui.getVisualisationPanel());
        return gui;
    }

    /**
     * Tests the PNG export functionality of the command.
     */
    @Test
    public void pngTestWithExtension() {
        MainWindow gui = this.buildGUI();
        
        CommandExportImage command = new CommandExportImage(gui.getConsolePanel(),
                ImageFormat.PNG, gui.getInterpreterManager());
        gui.getConsolePanel().clearAll();

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
    public void svgTestWithExtension() {
        MainWindow gui = this.buildGUI();
        
        CommandExportImage command = new CommandExportImage(gui.getConsolePanel(),
                ImageFormat.SVG, gui.getInterpreterManager());
        gui.getConsolePanel().clearAll();

        File test = new File("test.svg");
        command.exportSVG(test);

        assertTrue(test.exists());
        assertTrue(test.getName().equals("test.svg"));

        test.delete();
    }
}
