package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for executing a single interpreter step.
 * */
public class CommandNextStep extends Command {
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;
    
    /**
     * Initializes a new step command.
     * 
     * @param console               Panel of the console area
     * @param visualisation         Panel of the visualisation area
     * @param interpreterManager    Interpreter manager with a reference to the current interpreter
     */
    public CommandNextStep(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager interpreterManager) {
        this.console = console;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        final StepResult res = interpreterManager.step();
        if (res == StepResult.SOLUTION_FOUND) {
            // Print solution
        }
        
        // Update visualisation
    }
}
