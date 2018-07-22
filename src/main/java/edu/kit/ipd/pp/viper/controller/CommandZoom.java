package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for zooming in and out of the visualisation by a set amount.
 */
public class CommandZoom extends Command {
    private VisualisationPanel visualisation;
    private ZoomType direction;

    /**
     * Initializes a new zoom command.
     * 
     * @param visualisation Panel of the visualisation
     * @param direction Type of zoom (either in or out)
     */
    public CommandZoom(VisualisationPanel visualisation, ZoomType direction) {
        this.visualisation = visualisation;
        this.direction = direction;
    }

    @Override
    public void execute() {
        this.visualisation.zoom(this.direction);
    }
}
