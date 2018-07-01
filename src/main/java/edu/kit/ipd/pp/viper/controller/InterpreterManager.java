package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.StepResult;

/**
 * Manager class for interpreters. This class holds references to all
 * interpreters created and serves as an interface for commands to control the
 * interpretation and create new interpreters. In practice, this manager should
 * be treated like a singleton without global state, meaning there should only
 * be one instance which can be accessed by reference passed as a parameter.
 */
public class InterpreterManager {
    private boolean useStandardLibrary = false;

    /**
     * Initializes a new interpreter manager. This should only be called once.
     */
    public InterpreterManager() {
        // TODO
    }

    /**
     * Creates a new interpreter from source. This method calls the parser to create
     * a KnowledgeBase and creates a query from which said new interpreter is built.
     * The new interpreter then gets added to the internal container.
     * 
     * @param program Source code of the Prolog program to create a KnowledgeBase
     * from
     */
    public void createNew(String program) {
        // TODO
    }

    /**
     * Takes an interpreter step.
     * 
     * @return Result of the step taken
     */
    public StepResult step() {
        // TODO
        return null;
    }

    /**
     * Runs the interpreter until a new solution is found. This is done in a
     * separate thread to ensure the GUI is still responsive and the execution can
     * be canceled if it's going on for too long.
     */
    public void runUntilNextSolution() {
        // TODO
    }

    /**
     * Cancels any currently running search for the next solution invoked by calling
     * runUntilNextSolution() by stopping the thread. The currently executed
     * interpretation step is finished before the process is stopped. The
     * visualisation gets updated to the respective current step.
     */
    public void cancel() {
        // TODO
    }

    /**
     * Getter-Method for the current state of the interpretation.
     * 
     * @return Current state of the interpretation
     */
    public Interpreter getCurrentState() {
        // TODO
        return null;
    }

    /**
     * Enables the standard library.
     * */
    public void enableStandardLibrary() {
        this.useStandardLibrary = true;
    }

    /**
     * Disables the standard library.
     * */
    public void disableStandardLibrary() {
        this.useStandardLibrary = false;
    }

    /**
     * Returns whether the standard library is enabled.
     * 
     * @return boolean value describing whether the standard library is enabled
     * */
    public boolean isStandardEnabled() {
        return this.useStandardLibrary;
    }
}
