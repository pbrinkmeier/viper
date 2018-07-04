package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;
import guru.nidi.graphviz.model.Graph;

/**
 * Command for canceling the execution of the continue command.
 */
public class CommandCancel extends Command {
    private InterpreterManager interpreterManager;
    private VisualisationPanel visualisation;

    /**
     * Initializes a new cancel command.
     * 
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     * @param visualisation Panel of the visualisation
     */
    public CommandCancel(InterpreterManager interpreterManager, VisualisationPanel visualisation) {
        this.interpreterManager = interpreterManager;
        this.visualisation = visualisation;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        interpreterManager.cancel();
        Graph graph = GraphvizMaker.createGraph(interpreterManager.getCurrentState());
        visualisation.setFromGraph(graph);
    }
}