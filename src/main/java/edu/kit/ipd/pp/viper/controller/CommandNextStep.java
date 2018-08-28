package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;

import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for executing a single interpreter step.
 */
public class CommandNextStep extends Command {
    private final ConsolePanel console;
    private final VisualisationPanel visualisation;
    private final InterpreterManager manager;

    /**
     * Initializes a new step command.
     * 
     * @param console ConsolePanel for output
     * @param visualisation Panel of the visualisation area
     * @param manager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandNextStep(VisualisationPanel visualisation, InterpreterManager manager,
            ConsolePanel console) {
        this.console = console;
        this.visualisation = visualisation;
        this.manager = manager;
    }

    @Override
    public void execute() {
        this.manager.cancel();
        this.manager.nextStep(this.console);
        this.visualisation.setFromGraph(this.manager.getCurrentVisualisation());
    }
}
