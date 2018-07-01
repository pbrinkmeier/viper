package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for exporting the visualisation to a supported image format (PNG or SVG).
 * */
public class CommandExportImage extends Command {
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private ImageFormat format;
    private InterpreterManager interpreterManager;
    
    /**
     * Initializes a new image export command.
     * 
     * @param console               Panel of the console area
     * @param visualisation         Panel of the visualisation area
     * @param format                Format of the image to be saved
     * @param interpreterManager    Interpreter manager with a reference to the current interpreter
     */
    public CommandExportImage(ConsolePanel console, VisualisationPanel visualisation, ImageFormat format,
            InterpreterManager interpreterManager) {
        this.console = console;
        this.visualisation = visualisation;
        this.format = format;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        // TODO
    }
}