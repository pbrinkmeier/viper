package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandExportTikzTest {
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
    }

    /**
     * Tests the TikZ export functionality of the command.
     */
    @Test
    public void tikzTestWithExtension() {
    	CommandExportTikz command = new CommandExportTikz(this.console, this.interpreterManager);
        this.console.clearAll();

        File test = new File("test.tikz");
        command.exportTikZ(test);

        LanguageManager langman = LanguageManager.getInstance();
        final String output = langman.getString(LanguageKey.EXPORT_FILE_SUCCESS) + ": " + test.getAbsolutePath();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("test.tikz"));
        assertTrue(this.console.getOutputAreaText().trim().equals(output.trim()));

        test.delete();
    }
}
