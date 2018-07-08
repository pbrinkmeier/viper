package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command to toggle the usage of the standard library.
 */
public class CommandToggleLib extends Command {
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new toggle library command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     */
    public CommandToggleLib(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager interpreterManager) {
        this.console = console;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        this.interpreterManager.toggleStandardLibrary();
        this.console.clearAll();
        this.visualisation.clearVisualization();
    }
}
