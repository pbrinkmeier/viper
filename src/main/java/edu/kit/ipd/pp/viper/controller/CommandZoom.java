package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for zooming in and out of the visualisation by a set amount.
 */
public class CommandZoom extends Command {
    private final VisualisationPanel visualisation;
    private final ConsolePanel console;
    private final EditorPanel editor;
    private final ZoomType direction;

    /**
     * Initializes a new zoom command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param direction Type of zoom (either in or out)
     */
    public CommandZoom(VisualisationPanel visualisation, ConsolePanel console,
            EditorPanel editor, ZoomType direction) {
        super();

        this.visualisation = visualisation;
        this.console = console;
        this.editor = editor;
        this.direction = direction;
    }

    @Override
    public void execute() {
        if (this.console != null) {
            this.console.zoomOutputArea(this.direction);
        }

        if (this.editor != null) {
            this.editor.zoom(this.direction);
        }
        
        if (this.visualisation != null) {
            this.visualisation.zoom(this.direction);
        }
    }
}
