package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;
import edu.kit.ipd.pp.viper.model.parser.ParseException;

public class InterpreterManagerTest extends ControllerTest {
    /**
     * Tests resetting the instance to make it ready for a
     * new interpreter.
     */
    @Test
    public void resetTest() {
        this.gui.getEditorPanel().setSourceText("test(X) :- test(X).");
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();
        this.gui.getCommandNextSolution().execute();
        this.gui.getInterpreterManager().reset();
                
        assertFalse(this.gui.getInterpreterManager().isSearchingForSolutions());
    }
    
    /**
     * Tests whether parsing a query without
     * a knowledge base properly returns immediately.
     * @throws ParseException 
     */
    @Test(expected = ParseException.class) 
    public void queryWithoutKnowledgeBaseTest() throws ParseException {
        this.gui.getInterpreterManager().parseQuery("test(X).");        
    }
    
    /**
     * Tests issuing a single goal and retrieving the solution.
     * Due to threading issues during testing, this is achieved
     * using a fixed amount of steps.
     */
    @Test
    public void singleGoalTest() {
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(),
                this.gui::switchClickableState).execute();

        final int stepsNeeded = 2;
        for (int i = 0; i < stepsNeeded; i++) {
            this.gui.getCommandNextStep().execute();            
        }
                
        List<Substitution> solution = this.gui.getInterpreterManager().getSolution();
        assertTrue(solution.contains(new Substitution(new Variable("X"), new Functor("homer", Arrays.asList()))));
    }
    
    /**
     * Tests issuing a multigoal and retrieving the solution.
     * Due to threading issues during testing, this is achieved
     * using a fixed amount of steps.
     */
    @Test
    public void multipleGoalsTest() {
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("father(X, bart), father(Y, lisa).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();

        final int stepsNeeded = 6;
        for (int i = 0; i < stepsNeeded; i++) {
            this.gui.getCommandNextStep().execute();            
        }
        
        List<Substitution> solution = this.gui.getInterpreterManager().getSolution();
        assertTrue(solution.contains(new Substitution(new Variable("X"), new Functor("homer", Arrays.asList()))));
        assertTrue(solution.contains(new Substitution(new Variable("Y"), new Functor("homer", Arrays.asList()))));
    }
    
    /**
     * Tests threading functions, e.g. waiting for a thread that isn't started,
     * trying to finish a query while another finish query operation is already running
     * or trying to assign a new thread while a thread is already assigned.
     */
    @Test
    public void threadingOperationsTest() {
        InterpreterManager intman = this.gui.getInterpreterManager();

        // Test waiting for a thread that isn't started yet
        intman.waitForThread();
        
        // Test safety of trying to finish a query multiple times without always waiting
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(), intman,
                this.gui::switchClickableState).execute();
        intman.finishQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.assignFinishQueryThread(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();
        intman.assignFinishQueryThread(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();
        intman.assignFinishQueryThread(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();

        this.gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(), intman,
                this.gui::switchClickableState).execute();
        intman.finishQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.finishQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();
        
        // Test safety of trying to find the next solution multiple times without always waiting
        this.gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(), intman,
                this.gui::switchClickableState).execute();
        intman.nextSolution(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.assignNextSolutionThread(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();
        intman.assignNextSolutionThread(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();
        intman.assignNextSolutionThread(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();
        
        this.gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(), intman,
                this.gui::switchClickableState).execute();
        intman.nextSolution(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.nextSolution(this.gui.getConsolePanel(), this.gui.getVisualisationPanel());
        intman.waitForThread();
    }
    
    /**
     * Tests edge cases for doing single steps (e.g. next step without an interpreter,
     * doing the last step of an interpretation etc.) and running to the next solution
     * in different locations.
     */
    @Test
    public void steppingEdgeCasesTest() {
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getCommandParse().execute();

        this.gui.getInterpreterManager().nextStep(this.gui.getConsolePanel());
        
        this.gui.getConsolePanel().setInputFieldText("father(homer, bart).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(),
                this.gui::switchClickableState).execute();
        
        this.gui.getCommandNextStep().execute();
        this.gui.getCommandPreviousStep().execute();
        
        new CommandFinishQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager()).execute();
        this.gui.getInterpreterManager().waitForThread();
        
        this.gui.getCommandPreviousStep().execute();
        this.gui.getCommandNextSolution().execute();
        this.gui.getInterpreterManager().waitForThread();
        
        this.gui.getCommandPreviousStep().execute();
        this.gui.getCommandPreviousStep().execute();
        this.gui.getCommandNextStep().execute();
        this.gui.getCommandNextStep().execute();
    }
}
