package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CommandCancelTest extends ControllerTest {
    /**
     * Tests whether the cancel button properly
     * cancels a running search for a solution.
     */
    @Test
    public void cancelTest() {
        this.gui.getEditorPanel().setSourceText("test(X) :- test(X).");
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();
        this.gui.getCommandContinue().execute();
        this.gui.getCommandCancel().execute();
        
        assertFalse(this.gui.getInterpreterManager().isSearchingForSolutions());
    }
}
