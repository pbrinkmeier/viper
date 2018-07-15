package edu.kit.ipd.pp.viper.controller;

import java.util.List;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.interpreter.Environment;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;
import static java.util.stream.Collectors.toList;
import java.util.Optional;

/**
 * Manager class for interpreters. This class holds references to all
 * interpreters created and serves as an interface for commands to control the
 * interpretation and create new interpreters. In practice, this manager should
 * be treated like a singleton without global state, meaning there should only
 * be one instance which can be accessed by reference passed as a parameter.
 */
public class InterpreterManager {
    private Optional<KnowledgeBase> knowledgeBase;
    private Optional<Goal> query;
    private Optional<Interpreter> interpreter;
    private Optional<List<Variable>> variables;

    /**
     * Initializes an interpreter manager. This method calls reset() internally.
     */
    public InterpreterManager() {
        this.reset();
    }

    /**
     * Resets the instance to make it ready for a new interpreter.
     */
    public void reset() {
        this.knowledgeBase = Optional.empty();
        this.query = Optional.empty();
        this.interpreter = Optional.empty();
        this.variables = Optional.empty();
    }

    /**
     * Parses a knowledgebase and remembers it.
     *
     * @param kbSource source code to parse
     * @throws ParseException if the source code is malformed
     */
    public void parseKnowledgeBase(String kbSource) throws ParseException {
        this.knowledgeBase = Optional.of(new PrologParser(kbSource).parse());
    }

    /**
     * Parses a query and initializes an interpreter, if a parsed knowledgebase is
     * available.
     *
     * @param querySource source code of the query to parse
     * @throws ParseException if the source code is malformed
     */
    public void parseQuery(String querySource) throws ParseException {
        this.query = Optional.of(new PrologParser(querySource).parseGoalList().get(0));

        if (!this.knowledgeBase.isPresent()) {
            return;
        }

        this.interpreter = Optional.of(new Interpreter(this.knowledgeBase.get(), this.query.get()));
        this.variables = Optional.of(this.query.get().getVariables());
    }

    /**
     * Takes an interpreter step. If this method is called after a call to
     * {@link #reset()} (that includes the constructor) and before calls to
     * {@link #parseKnowledgeBase(String)} and {@link #parseQuery(String)}, i.e.
     * before an interpreter instance has been created, it will have no effect and
     * return null.
     * 
     * @return Result of the step taken
     */
    public StepResult step() {
        if (!this.interpreter.isPresent()) {
            return null;
        }

        return this.interpreter.get().step();
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
        /*
         * if (!this.searchingForSolution) { this.searchingForSolution = true;
         * this.continueThread = new Thread(() -> { StepResult result =
         * StepResult.STEPS_REMAINING; while (this.searchingForSolution) { result =
         * step(); this.searchingForSolution = result == StepResult.STEPS_REMAINING; }
         * 
         * Graph graph = GraphvizMaker.createGraph(getCurrentState());
         * visualisation.setFromGraph(graph); searchingForSolution = false; });
         * this.continueThread.start(); }
         */
    }

    /**
     * Cancels any currently running search for the next solution invoked by calling
     * runUntilNextSolution() by stopping the thread. The currently executed
     * interpretation step is finished before the process is stopped. The
     * visualisation gets updated to the respective current step.
     */
    public void cancel() {
        /**
         * this.searchingForSolution = false;
         */
    }

    /**
     * Returns a solution for the query. This method assumes that on the current
     * interpreter instance, interpreter.getCurrent().isPresent() holds. This means
     * that at least one step must have been executed.
     * 
     * @return string list of substitutions that form a solution
     */
    public List<Substitution> getSolution() {
        Environment currentEnv = this.getCurrentState().getCurrent().get().getEnvironment();

        List<Substitution> solution = this.variables.get().stream().map(variable -> {
            return new Substitution(variable, currentEnv.applyAllSubstitutions(variable));
        }).collect(toList());

        return solution;
    }

    /**
     * Getter-Method for the current state of the interpretation.
     * 
     * @return Current state of the interpretation
     */
    public Interpreter getCurrentState() {
        return this.interpreter.get();
    }

    /**
     * Toggles the standard library on or off.
     */
    public void toggleStandardLibrary() {
        /*
         * this.useStandardLibrary = !this.useStandardLibrary;
         */
    }

    /**
     * Returns whether the standard library is enabled.
     * 
     * @return boolean value describing whether the standard library is enabled
     */
    public boolean isStandardEnabled() {
        /*
         * return this.useStandardLibrary;
         */
        return false;
    }
}
