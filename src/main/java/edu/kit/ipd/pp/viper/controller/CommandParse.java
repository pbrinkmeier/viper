package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
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
    private InterpreterManager interpreterManager;
    private Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new parse command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager interpreter manager
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *            elements in the GUI
     */
    public CommandParse(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            InterpreterManager interpreterManager, Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.console.lockInput();

        this.interpreterManager.reset();

        try {
            this.interpreterManager.parseKnowledgeBase(editor.getSourceText());

            this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.PARSER_SUCCESS),
                    LogType.SUCCESS);
            this.console.unlockInput();
            this.toggleStateFunc.accept(ClickableState.PARSED_PROGRAM);
        } catch (ParseException e) {
            String prefix = LanguageManager.getInstance().getString(LanguageKey.PARSER_ERROR);
            String parserError = e.getMessage();

            this.console.printLine(String.format("%s: %s", prefix, parserError), LogType.ERROR);

            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
        }
    }
}
