package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for parsing the entered Prolog code.
 */
public class CommandParse extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;

    /**
     * Initializes a new parse command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     */
    public CommandParse(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        try {
            new PrologParser(editor.getSourceText()).parse();
            console.unlockInput();
        } catch (ParseException e) {
            console.printLine(LanguageManager.getInstance().getString(LanguageKey.PARSER_ERROR), LogType.INFO);
            console.lockInput();
            e.printStackTrace();
        }

        this.console.clearAll();
        this.visualisation.clearVisualization();
    }
}
