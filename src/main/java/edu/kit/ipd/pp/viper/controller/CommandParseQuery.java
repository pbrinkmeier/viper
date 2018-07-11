package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for parsing the entered Prolog code.
 */
public class CommandParseQuery extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;
    private Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new parse query command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     * @param toggleStateFunc Consumer function that switches the state of clickable
     * elements in the GUI
     */
    public CommandParseQuery(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            InterpreterManager interpreterManager, Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
        this.toggleStateFunc = toggleStateFunc;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        this.interpreterManager.createNew(this.editor.getSourceText(), this.console.getText(), this.console);
        this.visualisation.clearVisualization();
        this.console.clearAll();
        this.toggleStateFunc.accept(ClickableState.PARSED_QUERY);
    }
}
