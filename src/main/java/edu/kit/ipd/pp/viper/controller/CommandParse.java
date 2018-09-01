package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;
import java.util.List;
import static java.util.stream.Collectors.joining;

import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.view.ClickableState;
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
    private Consumer<ClickableState> toggleStateFunc;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new parse command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager interpreter manager
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     */
    public CommandParse(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            InterpreterManager interpreterManager, Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.interpreterManager = interpreterManager;
    }

    @Override
    public void execute() {
        this.interpreterManager.reset();

        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.console.lockInput();
        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);

        LanguageManager langman = LanguageManager.getInstance();
        try {
            List<Rule> conflictingRules = this.interpreterManager.parseKnowledgeBase(this.editor.getSourceText());
            this.console.printLine(langman.getString(LanguageKey.PARSER_SUCCESS), LogType.SUCCESS);
    
            if (!conflictingRules.isEmpty()) {
                String cRules = conflictingRules.stream()
                .map(rule -> rule.getHead().getShortNotation())
                .collect(joining(", "));

                this.console.printLine(
                    String.format(langman.getString(LanguageKey.CONFLICTING_RULES), cRules), LogType.INFO);
            }

            this.console.unlockInput();
            this.toggleStateFunc.accept(ClickableState.PARSED_PROGRAM);
        } catch (ParseException e) {
            String prefix = langman.getString(LanguageKey.PARSER_ERROR);
            String parserError = e.getMessage();
            this.console.printLine(String.format("%s: %s", prefix, parserError), LogType.ERROR);
        }
    }
}
