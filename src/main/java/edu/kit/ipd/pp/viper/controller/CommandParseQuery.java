package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;
import guru.nidi.graphviz.model.Graph;

/**
 * Command for parsing the entered Prolog code.
 */
public class CommandParseQuery extends Command {
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;
    private Consumer<ClickableState> toggleStateFunc;

    /**
     * Initializes a new parse query command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     * @param toggleStateFunc Consumer function that switches the state of clickable
     * elements in the GUI
     */
    public CommandParseQuery(ConsolePanel console, VisualisationPanel visualisation,
            InterpreterManager interpreterManager, Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.visualisation = visualisation;
        this.interpreterManager = interpreterManager;
        this.toggleStateFunc = toggleStateFunc;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        try {
            this.interpreterManager.parseQuery(this.console.getInputFieldText());
            Graph graph = GraphvizMaker.createGraph(this.interpreterManager.getCurrentState());
            this.visualisation.setFromGraph(graph);
            this.toggleStateFunc.accept(ClickableState.PARSED_QUERY);
            this.console.clearInputField();
            this.console.printLine(
                LanguageManager.getInstance().getString(LanguageKey.VISUALISATION_STARTED), LogType.SUCCESS);
        } catch (ParseException e) {
            String prefix = LanguageManager.getInstance().getString(LanguageKey.PARSER_QUERY_ERROR);
            String parserError = e.getMessage();

            this.console.printLine(String.format("%s: %s", prefix, parserError), LogType.ERROR);
        }
    }
}
