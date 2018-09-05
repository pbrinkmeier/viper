package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;

public class CommandFinishQueryTest extends ControllerTest {
    /**
     * Tests finishing a query.
     */
    @Test
    public void finishQueryTest() {
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(),
                this.gui::switchClickableState).execute();

        this.gui.getConsolePanel().clearOutputArea();
        this.gui.getCommandFinishQuery().execute();
        this.gui.getInterpreterManager().waitForThread();
        
        List<Substitution> solution = this.gui.getInterpreterManager().getSolution();
        assertFalse(this.gui.getConsolePanel().getOutputAreaText().isEmpty());
        assertFalse(solution.isEmpty());
        assertTrue(solution.get(0).equals(new Substitution(new Variable("X"), new Variable("X"))));
    }
}
