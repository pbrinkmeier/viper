package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command to toggle the usage of the standard library.
 */
public class CommandToggleLib extends Command {
    private final ConsolePanel console;
    private final VisualisationPanel visualisation;
    private final InterpreterManager intManager;
    private final PreferencesManager prefManager;
    private final Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new toggle library command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param intManager Interpreter manager with a reference to the current
     *        interpreter
     * @param prefManager Preferences manager
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     */
    public CommandToggleLib(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager intManager, PreferencesManager prefManager,
            Consumer<ClickableState> toggleStateFunc) {
        super();

        this.console = console;
        this.visualisation = visualisation;
        this.intManager = intManager;
        this.prefManager = prefManager;
        this.toggleStateFunc = toggleStateFunc;
    }

    @Override
    public void execute() {
        this.intManager.toggleStandardLibrary();
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);
        this.prefManager.setStandardLibEnabled(this.prefManager.isStandardLibEnabled() ? false : true);
    }
}
