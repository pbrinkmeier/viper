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
            ConsolePanel console) {
        this.console = console;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();

        this.interpreterManager.nextStep(this.console);

        this.visualisation.setFromGraph(this.interpreterManager.getCurrentVisualisation());
    }
}
