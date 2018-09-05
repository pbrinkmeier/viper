package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for parsing the entered Prolog code.
 */
public class CommandParseQuery extends Command {
    private final ConsolePanel console;
    private final VisualisationPanel visualisation;
    private final Consumer<ClickableState> toggleStateFunc;
    private final InterpreterManager manager;

    /**
     * Initializes a new parse query command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param manager Interpreter manager with a reference to the current
     *        interpreter
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     */
    public CommandParseQuery(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager manager, Consumer<ClickableState> toggleStateFunc) {
        super();

        this.console = console;
        this.visualisation = visualisation;
        this.manager = manager;
        this.toggleStateFunc = toggleStateFunc;
    }

    @Override
    public void execute() {
        this.manager.cancel();

        LanguageManager langman = LanguageManager.getInstance();
        try {
            this.manager.parseQuery(this.console.getInputFieldText());
            this.visualisation.setFromGraph(this.manager.getCurrentVisualisation());
            this.toggleStateFunc.accept(ClickableState.FIRST_STEP);
            this.console.clearInputField();
            this.console.printLine(langman.getString(LanguageKey.VISUALISATION_STARTED), LogType.INFO);
        } catch (ParseException e) {
            String prefix = langman.getString(LanguageKey.PARSER_QUERY_ERROR);
            String parserError = e.getMessage();
            this.console.printLine(String.format("%s: %s", prefix, parserError), LogType.ERROR);
        }
    }
}
