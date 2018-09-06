package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for resetting the zoom on all zoomable elements.
 */
public class CommandResetZoom extends Command {
    private final VisualisationPanel visualisation;
    private final ConsolePanel console;
    private final EditorPanel editor;

    /**
     * Initializes a new reset zoom command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     */
    public CommandResetZoom(VisualisationPanel visualisation, ConsolePanel console, EditorPanel editor) {
        super();

        this.visualisation = visualisation;
        this.console = console;
        this.editor = editor;
    }
    
    @Override
    public void execute() {
        if (this.visualisation != null) {
            this.visualisation.resetZoom();            
        }

        if (this.console != null) {
            this.console.resetZoom();            
        }

        if (this.editor != null) {
            this.editor.resetZoom();            
        }
    }
}
