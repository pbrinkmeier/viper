package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandParseQueryTest {
    private MainWindow gui;
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.console = this.gui.getConsolePanel();
        this.visualisation = this.gui.getVisualisationPanel();
        this.interpreterManager = this.gui.getInterpreterManager();
    }
    
    /**
     * Tests issuing a query with valid syntax.
     */
    @Test
    public void testValidQuery() {
        this.gui.getCommandParse().execute();
        this.console.setInputFieldText("validQuery(X).");
        
        this.visualisation.clearVisualization();
        assertTrue(this.visualisation.showsPlaceholder());
        
        new CommandParseQuery(this.console, this.visualisation, this.interpreterManager,
                this.gui::switchClickableState).execute();
        
        assertTrue(this.visualisation.hasGraph());
        assertTrue(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }

    /**
     * Tests issuing a query with invalid syntax.
     */
    @Test
    public void testInvalidQuery() {
        this.gui.getCommandParse().execute();
        this.console.setInputFieldText("invalidQuery(");
        
        this.visualisation.clearVisualization();
        assertTrue(this.visualisation.showsPlaceholder());
        
        new CommandParseQuery(this.console, this.visualisation, this.interpreterManager,
                this.gui::switchClickableState).execute();
        
        assertTrue(this.visualisation.showsPlaceholder());
        assertFalse(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }
}
