package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

import guru.nidi.graphviz.model.Graph;

/**
 * Command for parsing the entered Prolog code.
 */
public class CommandParseQuery extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new parse query command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     */
    public CommandParseQuery(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
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
        try {
            this.interpreterManager.parseQuery(this.console.getText());
            Graph graph = GraphvizMaker.createGraph(this.interpreterManager.getCurrentState());
            this.visualisation.setFromGraph(graph);
            this.console.printLine(
                LanguageManager.getInstance().getString(LanguageKey.VISUALISATION_STARTED), LogType.SUCCESS);
        } catch (ParseException e) {
            String prefix = LanguageManager.getInstance().getString(LanguageKey.PARSER_QUERY_ERROR);
            String parserError = e.getMessage();

            this.console.printLine(String.format("%s: %s", prefix, parserError), LogType.ERROR);
        }
    }
}
