package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for continuously executing interpreter-steps until the next solution
 * is found. Once a solution is found, it is printed out through the console and
 * the visualisation adapts to the respective interpreter state.
 */
public class CommandNextSolution extends Command {
    private final ConsolePanel console;
    private final VisualisationPanel visualisation;
    private final InterpreterManager manager;

    /**
     * Initializes a new continue command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param manager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandNextSolution(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager manager) {
        super();

        this.console = console;
        this.visualisation = visualisation;
        this.manager = manager;
    }

    @Override
    public void execute() {
        this.manager.cancel();
        this.manager.nextSolution(this.console, this.visualisation);
    }
}
