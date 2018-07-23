package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for canceling the execution of the continue command.
 */
public class CommandCancel extends Command {
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new cancel command.
     * 
     * @param visualisation Panel of the visualisation
     * @param interpreterManager Interpreter manager with a reference to the current
     *            interpreter
     */
    public CommandCancel(InterpreterManager interpreterManager) {
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        this.interpreterManager.cancel();
    }
}
