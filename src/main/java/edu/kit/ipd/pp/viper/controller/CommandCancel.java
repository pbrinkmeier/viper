package edu.kit.ipd.pp.viper.controller;

/**
 * Command for canceling the execution of the continue command.
 */
public class CommandCancel extends Command {
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new cancel command.
     * 
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     */
    public CommandCancel(InterpreterManager interpreterManager) {
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        interpreterManager.cancel();

        // Update visualisation
    }
}