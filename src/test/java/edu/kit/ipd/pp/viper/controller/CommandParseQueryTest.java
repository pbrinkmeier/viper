package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandParseQueryTest {
    /**
     * Tests issuing a query with valid syntax.
     */
    @Test
    public void testValidQuery() {
        MainWindow gui = new MainWindow(false);

        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("validQuery(X).");
        
        gui.getVisualisationPanel().clearVisualization();
        assertTrue(gui.getVisualisationPanel().showsPlaceholder());
        
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui::switchClickableState).execute();
        
        assertTrue(gui.getVisualisationPanel().hasGraph());
        assertTrue(gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }

    /**
     * Tests issuing a query with invalid syntax.
     */
    @Test
    public void testInvalidQuery() {
        MainWindow gui = new MainWindow(false);
        
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("invalidQuery(");
        
        gui.getVisualisationPanel().clearVisualization();
        assertTrue(gui.getVisualisationPanel().showsPlaceholder());
        
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui::switchClickableState).execute();
        
        assertTrue(gui.getVisualisationPanel().showsPlaceholder());
        assertFalse(gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }
}
