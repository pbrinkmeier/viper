package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;

public class CommandNextSolutionTest extends ControllerTest {
    /**
     * Tests finding the first solution for an example query.
     */
    @Test
    public void nextSolutionTest() {
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(),
                this.gui::switchClickableState).execute();

        this.gui.getConsolePanel().clearOutputArea();
        
        new CommandNextSolution(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager()).execute();
        this.gui.getInterpreterManager().waitForThread();
        
        List<Substitution> solution = this.gui.getInterpreterManager().getSolution();
        assertFalse(this.gui.getConsolePanel().getOutputAreaText().isEmpty());
        assertFalse(solution.isEmpty());
        assertTrue(solution.get(0).equals(new Substitution(new Variable("X"), new Functor("homer", Arrays.asList()))));
    }
}
