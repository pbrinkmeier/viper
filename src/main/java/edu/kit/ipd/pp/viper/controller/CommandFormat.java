package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;

/**
 * Command for formatting the editor content to a standardized format.
 */
public class CommandFormat extends Command {
    private ConsolePanel console;
    private EditorPanel editor;

    /**
     * Initializes a new format command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the visualisation area
     */
    public CommandFormat(ConsolePanel console, EditorPanel editor) {
        this.console = console;
        this.editor = editor;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        // TODO
    }
}
