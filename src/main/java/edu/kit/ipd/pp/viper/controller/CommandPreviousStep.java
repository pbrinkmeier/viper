package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for returning to the interpreter step before the current one.
 */
public class CommandPreviousStep extends Command {
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new previous step command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandPreviousStep(VisualisationPanel visualisation, InterpreterManager interpreterManager) {
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();
        this.interpreterManager.previousStep();
        this.visualisation.setFromGraph(this.interpreterManager.getCurrentVisualisation());
    }
}
