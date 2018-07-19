package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.MainWindow;

/**
 * Command to toggle the usage of the standard library.
 */
public class CommandToggleLib extends Command {
    private MainWindow main;
    private Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new toggle library command.
     * 
     * @param gui The main window containing all panels, as well as the interpreter manager
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *            elements in the GUI
     */
    public CommandToggleLib(MainWindow gui, Consumer<ClickableState> toggleStateFunc) {
        this.main = gui;
        this.toggleStateFunc = toggleStateFunc;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        this.main.getInterpreterManager().toggleStandardLibrary();
        this.main.getConsolePanel().clearAll();
        this.main.getVisualisationPanel().clearVisualization();
        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);

        this.main.getPreferencesManager().setStandardLibEnabled(
                this.main.getPreferencesManager().isStandardLibEnabled() ? false : true);
    }
}
