package edu.kit.ipd.pp.viper.controller;

import java.util.Locale;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for setting the GUI language.
 */
public class CommandSetLang extends Command {
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private Locale lang;
    private InterpreterManager interpreterManager;
    private PreferencesManager prefManager;

    /**
     * Initializes a new set language command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param lang Language to be switched to
     * @param interpreterManager Interpreter manager with a reference to the current
     *        interpreter
     * @param prefManager Preferences manager
     */
    public CommandSetLang(ConsolePanel console, VisualisationPanel visualisation, Locale lang,
            InterpreterManager interpreterManager, PreferencesManager prefManager) {
        this.console = console;
        this.visualisation = visualisation;
        this.lang = lang;
        this.interpreterManager = interpreterManager;
        this.prefManager = prefManager;
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();

        LanguageManager.getInstance().setLocale(this.lang);
        this.prefManager.setLanguage(this.lang);
        this.console.clearOutputArea();

        if (this.visualisation.hasGraph() && !this.visualisation.showsPlaceholder()) {
            this.visualisation.setFromGraph(this.interpreterManager.getCurrentVisualisation());
        }
    }
}
