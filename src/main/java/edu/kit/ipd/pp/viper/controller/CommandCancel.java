package edu.kit.ipd.pp.viper.controller;

/**
 * Command for canceling the execution of the continue command.
 */
public class CommandCancel extends Command {
    private final InterpreterManager manager;

    /**
     * Initializes a new cancel command.
     * 
     * @param manager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandCancel(InterpreterManager manager) {
        super();
        this.manager = manager;
    }

    @Override
    public void execute() {
        this.manager.cancel();
    }
}
