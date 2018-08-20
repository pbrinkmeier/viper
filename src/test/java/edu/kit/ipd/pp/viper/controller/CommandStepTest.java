package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandStepTest {
    /**
     * Tests doing a step back on the previous step, then doing a
     * single step forward and then a step back again.
     * 
     * The interpretation state is determined by the clickable state
     * as the interpreter manager doesn't expose more exact information.
     * Proper stepping is tested further in model tests.
     */
    @Test
    public void testSingleStepping() {
        MainWindow gui = new MainWindow(false);
        
        gui.getEditorPanel().setSourceText("test(X) :- test(X).");
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui::switchClickableState).execute();
        
        assertFalse(gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(gui.getVisualisationPanel().hasGraph());
        assertTrue(gui.getClickableState().equals(ClickableState.FIRST_STEP));
        gui.getCommandPreviousStep().execute();
        assertFalse(gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(gui.getVisualisationPanel().hasGraph());
        assertTrue(gui.getClickableState().equals(ClickableState.FIRST_STEP));
        gui.getCommandNextStep().execute();
        assertFalse(gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(gui.getVisualisationPanel().hasGraph());
        assertFalse(gui.getClickableState().equals(ClickableState.FIRST_STEP));
        assertTrue(gui.getClickableState().equals(ClickableState.PARSED_QUERY));
        gui.getCommandPreviousStep().execute();
        assertFalse(gui.getInterpreterManager().isSearchingForSolutions());
        assertTrue(gui.getVisualisationPanel().hasGraph());
        assertTrue(gui.getClickableState().equals(ClickableState.FIRST_STEP));
    }
}
