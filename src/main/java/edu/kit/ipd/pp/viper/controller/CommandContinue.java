package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for continuously executing interpreter-steps until the next solution is found.
 * Once a solution is found, it is printed out through the console and the visualisation
 * adapts to the respective interpreter state.
 * */
public class CommandContinue extends Command {
    /**
     * Initializes a new continue command.
     * 
     * @param console               Panel of the console area
     * @param visualisation         Panel of the visualisation area
     * @param interpreterManager    Interpreter manager with a reference to the current interpreter
     */
    public CommandContinue(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager interpreterManager) {
        // TODO
    }

    /**
     * Executes the command.
     */
    public void execute() {
        // TODO
    }
}