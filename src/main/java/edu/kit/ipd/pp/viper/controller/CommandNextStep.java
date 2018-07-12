package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;
import guru.nidi.graphviz.model.Graph;

/**
 * Command for executing a single interpreter step.
 */
public class CommandNextStep extends Command {
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new step command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     */
    public CommandNextStep(VisualisationPanel visualisation,
            InterpreterManager interpreterManager) {
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        final StepResult res = this.interpreterManager.step();

        Graph graph = GraphvizMaker.createGraph(this.interpreterManager.getCurrentState());
        this.visualisation.setFromGraph(graph);
    }
}
