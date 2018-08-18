package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandResetZoom extends Command {
    private VisualisationPanel visualisation;
    private ConsolePanel console;
    private EditorPanel editor;

    public CommandResetZoom(VisualisationPanel visualisation, ConsolePanel console, EditorPanel editor) {
        this.visualisation = visualisation;
        this.console = console;
        this.editor = editor;
    }
    
    @Override
    public void execute() {
        this.visualisation.resetZoom();
        this.console.resetZoom();
        this.editor.resetZoom();
    }
}
