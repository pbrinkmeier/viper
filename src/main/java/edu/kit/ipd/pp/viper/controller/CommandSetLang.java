package edu.kit.ipd.pp.viper.controller;

import java.util.Locale;

import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;
import guru.nidi.graphviz.model.Graph;

/**
 * Command for setting the GUI language.
 */
public class CommandSetLang extends Command {
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private Locale lang;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new set language command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param lang Language to be switched to
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     */
    public CommandSetLang(ConsolePanel console, VisualisationPanel visualisation, Locale lang,
            InterpreterManager interpreterManager) {
        this.console = console;
        this.visualisation = visualisation;
        this.lang = lang;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        LanguageManager.getInstance().setLocale(this.lang);
        console.clearAll();

        Graph graph = GraphvizMaker.createGraph(interpreterManager.getCurrentState());
        visualisation.setFromGraph(graph);
    }
}
