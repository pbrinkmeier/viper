package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command to toggle the usage of the standard library.
 */
public class CommandToggleLib extends Command {
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;
    private PreferencesManager preferencesManager;
    private Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new toggle library command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current interpreter
     * @param preferencesManager Preferences manager
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *            elements in the GUI
     */
    public CommandToggleLib(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager interpreterManager, PreferencesManager preferencesManager,
            Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
        this.preferencesManager = preferencesManager;
        this.toggleStateFunc = toggleStateFunc;
    }

    @Override
    public void execute() {
        this.interpreterManager.toggleStandardLibrary();
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);

        this.preferencesManager.setStandardLibEnabled(
                this.preferencesManager.isStandardLibEnabled() ? false : true
        );
    }
}
