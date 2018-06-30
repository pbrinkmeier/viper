package edu.kit.ipd.pp.viper.controller;

import java.util.Locale;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandSetLang extends Command {
    private ConsolePanel consolePanel;
    private VisualisationPanel visualisationPanel;
    private Locale lang;
    private InterpreterManager interpreterManager;

    /**
     * @param console 
     * @param visualisation 
     * @param lang 
     * @param interpreterManager
     */
    public CommandSetLang(ConsolePanel console, VisualisationPanel visualisation, Locale lang, InterpreterManager interpreterManager) {
        this.consolePanel = console;
        this.visualisationPanel = visualisation;
        this.lang = lang;
        this.interpreterManager = interpreterManager;
    }

    /**
     * @return
     */
    public void execute() {
        LanguageManager.getInstance().setLocale(this.lang);
    }
}
