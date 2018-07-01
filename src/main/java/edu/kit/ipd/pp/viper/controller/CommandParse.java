package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for parsing the entered Prolog code.
 * */
public class CommandParse extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;
    
    /**
     * Initializes a new parse command.
     * 
     * @param console               Panel of the console area
     * @param editor                Panel of the editor area
     * @param visualisation         Panel of the visualisation area
     * @param interpreterManager    Interpreter manager with a reference to the current interpreter
     */
    public CommandParse(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            InterpreterManager interpreterManager) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        // TODO
    }
}
