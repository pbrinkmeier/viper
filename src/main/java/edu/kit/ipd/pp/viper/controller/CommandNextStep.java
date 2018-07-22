package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;
import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.joining;

/**
 * Command for executing a single interpreter step.
 */
public class CommandNextStep extends Command {
    private final ConsolePanel console;
    private final VisualisationPanel visualisation;
    private final Consumer<ClickableState> toggleStateFunc;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new step command.
     * 
     * @param console ConsolePanel for output
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     *        interpreter
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     */
    public CommandNextStep(VisualisationPanel visualisation, InterpreterManager interpreterManager,
            ConsolePanel console, Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
        this.toggleStateFunc = toggleStateFunc;
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();

        StepResult result = this.interpreterManager.nextStep();

        if (result == null) {
            return;
        }

        this.toggleStateFunc.accept(ClickableState.PARSED_QUERY);

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
            this.toggleStateFunc.accept(ClickableState.LAST_STEP);
        }

        this.visualisation.setFromGraph(this.interpreterManager.getCurrentVisualisation());
    }
}
