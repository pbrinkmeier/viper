package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;
import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

import guru.nidi.graphviz.model.Graph;

import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.joining;

/**
 * Command for executing a single interpreter step.
 */
public class CommandNextStep extends Command {
    private final VisualisationPanel visualisation;
    private final InterpreterManager interpreterManager;
    private final ConsolePanel console;
    private final Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new step command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     *            interpreter
     * @param console ConsolePanel for output
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *            elements in the GUI
     */
    public CommandNextStep(VisualisationPanel visualisation, InterpreterManager interpreterManager,
            ConsolePanel console, Consumer<ClickableState> toggleStateFunc) {
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
        this.console = console;
        this.toggleStateFunc = toggleStateFunc;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        StepResult result = this.interpreterManager.step();

        if (result == null) {
            return;
        }

        if (result == StepResult.SOLUTION_FOUND) {
            String prefix = LanguageManager.getInstance().getString(LanguageKey.SOLUTION_FOUND);
            List<Substitution> solution = this.interpreterManager.getSolution();

            String solutionString = solution.size() == 0
                    ? ("  " + LanguageManager.getInstance().getString(LanguageKey.SOLUTION_YES))
                    : solution.stream().map(s -> "  " + s.toString()).collect(joining(",\n"));

            this.console.printLine(String.format("%s:\n%s.", prefix, solutionString), LogType.SUCCESS);
        }

        if (result == StepResult.NO_MORE_SOLUTIONS) {
            this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.NO_MORE_SOLUTIONS),
                    LogType.INFO);
            this.toggleStateFunc.accept(ClickableState.NO_MORE_SOLUTIONS);
        }

        Graph graph = GraphvizMaker.createGraph(this.interpreterManager.getCurrentState());
        this.visualisation.setFromGraph(graph);
    }
}
