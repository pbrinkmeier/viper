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
import edu.kit.ipd.pp.viper.view.MainWindow;

public class InterpreterManagerTest {
    /**
     * Tests resetting the instance to make it ready for a
     * new interpreter.
     */
    @Test
    public void testReset() {
        MainWindow gui = new MainWindow(false);
        
        gui.getEditorPanel().setSourceText("test(X) :- test(X).");
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui::switchClickableState).execute();
        gui.getCommandContinue().execute();
        gui.getInterpreterManager().reset();
                
        assertFalse(gui.getInterpreterManager().isSearchingForSolutions());
    }
    
    /**
     * Tests whether parsing a query without
     * a knowledge base properly returns immediately.
     * @throws ParseException 
     */
    @Test(expected = ParseException.class) 
    public void testQueryWithoutKnowledgeBase() throws ParseException {
        MainWindow gui = new MainWindow(false);
        gui.getInterpreterManager().parseQuery("test(X).");        
    }
    
    /**
     * Tests issuing a single goal and retrieving the solution.
     * Due to threading issues during testing, this is achieved
     * using a fixed amount of steps.
     */
    @Test
    public void testSingleGoal() {
        MainWindow gui = new MainWindow(false);
        gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("father(X, bart).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(), gui.getInterpreterManager(),
                gui::switchClickableState).execute();

        final int stepsNeeded = 2;
        for (int i = 0; i < stepsNeeded; i++)
            gui.getCommandNextStep().execute();
                
        List<Substitution> solution = gui.getInterpreterManager().getSolution();
        assertFalse(solution.isEmpty());
        assertTrue(solution.get(0).equals(new Substitution(new Variable("X"), new Functor("homer", Arrays.asList()))));
    }
    
    /**
     * Tests issuing a multigoal and retrieving the solution.
     * Due to threading issues during testing, this is achieved
     * using a fixed amount of steps.
     */
    @Test
    public void testMultipleGoals() {
        MainWindow gui = new MainWindow(false);
        gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("father(X, bart), father(Y, lisa).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(), gui.getInterpreterManager(),
                gui::switchClickableState).execute();

        final int stepsNeeded = 6;
        for (int i = 0; i < stepsNeeded; i++)
            gui.getCommandNextStep().execute();
        
        List<Substitution> solution = gui.getInterpreterManager().getSolution();
        assertFalse(solution.isEmpty());
        assertTrue(solution.get(0).equals(new Substitution(new Variable("X"), new Functor("homer", Arrays.asList()))));
        assertTrue(solution.get(1).equals(new Substitution(new Variable("Y"), new Functor("homer", Arrays.asList()))));
    }
}
