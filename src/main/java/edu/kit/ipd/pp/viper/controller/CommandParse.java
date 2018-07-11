package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for parsing the entered Prolog code.
 */
public class CommandParse extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new parse command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param toggleStateFunc Consumer function that switches the state of clickable
     * elements in the GUI
     */
    public CommandParse(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        try {
            new PrologParser(this.editor.getSourceText()).parse();
            this.console.clearAll();
            this.visualisation.clearVisualization();
            this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.PARSER_SUCCESS), LogType.INFO);
            this.console.unlockInput();
            this.toggleStateFunc.accept(ClickableState.PARSED_PROGRAM);
        } catch (ParseException e) {
            this.console.printLine(
                    LanguageManager.getInstance().getString(LanguageKey.PARSER_ERROR) + "\n" + e.getMessage(),
                    LogType.ERROR);
            this.console.lockInput();

            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
        }
    }
}
