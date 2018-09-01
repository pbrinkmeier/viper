package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ClickableState;

public class CommandStepTest extends ControllerTest {
    /**
     * Tests doing a step back on the previous step, then doing a
     * single step forward and then a step back again.
     * 
     * The interpretation state is determined by the clickable state
     * as the interpreter manager doesn't expose more exact information.
     * Proper stepping is tested further in model tests.
     */
    @Test
    public void singleSteppingTest() {
        this.gui.getEditorPanel().setSourceText("test(X) :- test(X).");
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();
        
        assertFalse(this.gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(this.gui.getVisualisationPanel().hasGraph());
        assertTrue(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
        this.gui.getCommandPreviousStep().execute();
        assertFalse(this.gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(this.gui.getVisualisationPanel().hasGraph());
        assertTrue(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
        this.gui.getCommandNextStep().execute();
        assertFalse(this.gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(this.gui.getVisualisationPanel().hasGraph());
        assertFalse(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
        assertTrue(this.gui.getClickableState().equals(ClickableState.PARSED_QUERY));
        this.gui.getCommandPreviousStep().execute();
        assertFalse(this.gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(this.gui.getVisualisationPanel().hasGraph());
        assertTrue(this.gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }
}
