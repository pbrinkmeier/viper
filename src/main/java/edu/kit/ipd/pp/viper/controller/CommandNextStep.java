package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 * Command for executing a single interpreter step.
 */
public class CommandNextStep extends Command {
    private final VisualisationPanel visualisation;
    private final InterpreterManager interpreterManager;
    private final ConsolePanel console;

    /**
     * Initializes a new step command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     *            interpreter
     * @param console ConsolePanel for output
     */
    public CommandNextStep(VisualisationPanel visualisation, InterpreterManager interpreterManager,
            ConsolePanel console) {
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
        this.console = console;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        StepResult result = this.interpreterManager.nextStep();

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
        }

        this.visualisation.setFromGraph(this.interpreterManager.getCurrentVisualisation());
    }
}
