package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for returning to the interpreter step before the current one.
 */
public class CommandPreviousStep extends Command {
    private final VisualisationPanel visualisation;
    private final InterpreterManager manager;

    /**
     * Initializes a new previous step command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param manager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandPreviousStep(VisualisationPanel visualisation, InterpreterManager manager) {
        super();

        this.visualisation = visualisation;
        this.manager = manager;
    }

    @Override
    public void execute() {
        this.manager.cancel();
        this.manager.previousStep();
        this.visualisation.setFromGraph(this.manager.getCurrentVisualisation());
    }
}
