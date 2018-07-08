package edu.kit.ipd.pp.viper.controller;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;
import guru.nidi.graphviz.model.Graph;

/**
 * Manager class for interpreters. This class holds references to all
 * interpreters created and serves as an interface for commands to control the
 * interpretation and create new interpreters. In practice, this manager should
 * be treated like a singleton without global state, meaning there should only
 * be one instance which can be accessed by reference passed as a parameter.
 */
public class InterpreterManager {
    private Thread continueThread;
    private boolean searchingForSolution = false;
    private boolean useStandardLibrary = false;

    private List<Interpreter> interpreters;

    /**
     * Initializes a new interpreter manager. This should only be called once.
     */
    public InterpreterManager() {
        interpreters = new ArrayList<Interpreter>();
    }

    /**
     * Creates a new interpreter from source. This method calls the parser to create
     * a KnowledgeBase and creates a query from which said new interpreter is built.
     * The new interpreter then gets added to the internal container.
     * 
     * @param program Source code of the Prolog program to create a KnowledgeBase
     * from
     * @param queryCode Source code of the query to be interpreted
     * @param console Panel of the console area
     */
    public void createNew(String program, String queryCode, ConsolePanel console) {
        KnowledgeBase kb = null;
        try {
            kb = new PrologParser(program).parse();
        } catch (ParseException e) {
            console.printLine(LanguageManager.getInstance().getString(LanguageKey.PARSER_ERROR), LogType.INFO);
            e.printStackTrace();
        }

        Goal query = null;
        try {
            query = new PrologParser(queryCode).parseGoalList().get(0);
        } catch (ParseException e) {
            console.printLine(LanguageManager.getInstance().getString(LanguageKey.PARSER_ERROR), LogType.INFO);
            e.printStackTrace();
        }

        Interpreter interpreter = new Interpreter(kb, query);
        interpreters.add(interpreter);
    }

    /**
     * Takes an interpreter step.
     * 
     * @return Result of the step taken
     */
    public StepResult step() {
        return this.getCurrentState().step();
    }

    /**
     * Takes an interpreter step back.
     * 
     * @return Result of the step taken
     */
    public StepResult stepBack() {
        return null;
    }

    /**
     * Runs the interpreter until a new solution is found. This is done in a
     * separate thread to ensure the GUI is still responsive and the execution can
     * be canceled if it's going on for too long.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     */
    public void runUntilNextSolution(ConsolePanel console, VisualisationPanel visualisation) {
        if (!this.searchingForSolution) {
            this.searchingForSolution = true;
            this.continueThread = new Thread(() -> {
                StepResult result = StepResult.STEPS_REMAINING;
                while (this.searchingForSolution) {
                    result = step();
                    this.searchingForSolution = result == StepResult.STEPS_REMAINING;
                }

                if (result == StepResult.SOLUTION_FOUND) {
                    final String prefix = LanguageManager.getInstance().getString(LanguageKey.SOLUTION_FOUND);
                    console.printLine(prefix + getSolutionString(), LogType.INFO);
                }

                Graph graph = GraphvizMaker.createGraph(getCurrentState());
                visualisation.setFromGraph(graph);
                searchingForSolution = false;
            });
            this.continueThread.start();
        }
    }

    /**
     * Cancels any currently running search for the next solution invoked by calling
     * runUntilNextSolution() by stopping the thread. The currently executed
     * interpretation step is finished before the process is stopped. The
     * visualisation gets updated to the respective current step.
     */
    public void cancel() {
        this.searchingForSolution = false;
    }

    /**
     * Returns an immutable list of substitutions that solve the query.
     * 
     * @return A possible solution in the form of an immutable list
     */
    public List<Substitution> getSolution() {
        return null;
    }

    /**
     * Returns a string representation of the solution for console output.
     * 
     * @return string representation of the substitutions found
     */
    public String getSolutionString() {
        final List<Substitution> solution = getSolution();
        String rv = "{";

        for (int i = 0; i < solution.size(); i++) {
            rv += solution.get(i).toString();
            rv += i < solution.size() - 1 ? ", " : "}";
        }

        return rv;
    }

    /**
     * Getter-Method for the current state of the interpretation.
     * 
     * @return Current state of the interpretation
     */
    public Interpreter getCurrentState() {
        return interpreters.get(interpreters.size() - 1);
    }

    /**
     * Toggles the standard library on or off.
     */
    public void toggleStandardLibrary() {
        this.useStandardLibrary = !this.useStandardLibrary;
    }

    /**
     * Returns whether the standard library is enabled.
     * 
     * @return boolean value describing whether the standard library is enabled
     */
    public boolean isStandardEnabled() {
        return this.useStandardLibrary;
    }
}
