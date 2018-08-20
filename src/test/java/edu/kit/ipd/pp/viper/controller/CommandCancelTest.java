package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandCancelTest {
    /**
     * Tests whether the cancel button properly
     * cancels a running search for a solution.
     */
    @Test
    public void testCancel() {
        MainWindow gui = new MainWindow(false);
        
        gui.getEditorPanel().setSourceText("test(X) :- test(X).");
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui::switchClickableState).execute();
        gui.getCommandContinue().execute();
        gui.getCommandCancel().execute();
        
        assertFalse(gui.getInterpreterManager().isSearchingForSolutions());
    }
}
