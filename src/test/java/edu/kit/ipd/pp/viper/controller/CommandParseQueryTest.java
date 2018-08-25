package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ClickableState;

public class CommandParseQueryTest extends ControllerTest {
    /**
     * Tests issuing a query with valid syntax.
     */
    @Test
    public void validQueryTest() {
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("validQuery(X).");
        
        this.gui.getVisualisationPanel().clearVisualization();
        assertTrue(this.gui.getVisualisationPanel().showsPlaceholder());
        
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();
        
        assertTrue(this.gui.getVisualisationPanel().hasGraph());
        assertTrue(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }

    /**
     * Tests issuing a query with invalid syntax.
     */
    @Test
    public void invalidQueryTest() {
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("invalidQuery(");
        
        this.gui.getVisualisationPanel().clearVisualization();
        assertTrue(this.gui.getVisualisationPanel().showsPlaceholder());
        
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();
        
        assertTrue(this.gui.getVisualisationPanel().showsPlaceholder());
        assertFalse(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }
}
