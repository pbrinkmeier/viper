package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandExportImageTest {
    private MainWindow gui;
    private ConsolePanel console;
    private InterpreterManager interpreterManager;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.console = this.gui.getConsolePanel();
        this.interpreterManager = this.gui.getInterpreterManager();

        final String program = SharedTestConstants.SIMPSONS_FORMATTED;
        final String query = SharedTestConstants.TEST_QUERY;
        try {
            this.interpreterManager.parseKnowledgeBase(program);
            this.interpreterManager.parseQuery(query);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.interpreterManager.nextSolution(this.console, this.gui.getVisualisationPanel());
    }

    /**
     * Tests the PNG export functionality of the command.
     */
    @Test
    public void pngTestWithExtension() {
        CommandExportImage command = new CommandExportImage(this.console, ImageFormat.PNG, this.interpreterManager);
        this.console.clearAll();

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
        CommandExportImage command = new CommandExportImage(this.console, ImageFormat.SVG, this.interpreterManager);
        this.console.clearAll();

        File test = new File("test.svg");
        command.exportSVG(test);

        assertTrue(test.exists());
        assertTrue(test.getName().equals("test.svg"));

        test.delete();
    }
}
