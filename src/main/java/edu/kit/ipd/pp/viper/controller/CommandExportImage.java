package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for exporting the visualisation to a supported image format (PNG or SVG).
 * */
public class CommandExportImage extends Command {
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
        // TODO
    }

    /**
     * Executes the command.
     */
    public void execute() {
        // TODO
    }
}