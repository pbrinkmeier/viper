package edu.kit.ipd.pp.viper.controller;

import java.util.function.Supplier;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for zooming in and out of the visualisation by a set amount.
 */
public class CommandZoom extends Command {
    private VisualisationPanel visualisation;
    private ConsolePanel console;
    private EditorPanel editor;
    private Supplier<String> comboBoxGetter;
    private ZoomType direction;

    /**
     * Initializes a new zoom command.
     * 
     * @param visualisation Panel of the visualisation area
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param comboBoxGetter Supplier function that yields the current state of the zoom target combo box
     * @param direction Type of zoom (either in or out)
     * 
     */
    public CommandZoom(VisualisationPanel visualisation, ConsolePanel console, EditorPanel editor,
            Supplier<String> comboBoxGetter, ZoomType direction) {
        this.visualisation = visualisation;
        this.console = console;
        this.editor = editor;
        this.direction = direction;
        this.comboBoxGetter = comboBoxGetter;
    }

    @Override
    public void execute() {
        LanguageManager langman = LanguageManager.getInstance();
        
        final String comboBoxVal = this.comboBoxGetter.get();
        if (comboBoxVal.equals(langman.getString(LanguageKey.ZOOM_TARGET_VIS))) {
            this.visualisation.zoom(this.direction);
        } else if (comboBoxVal.equals(langman.getString(LanguageKey.ZOOM_TARGET_CO))) {
            this.console.zoomOutputArea(this.direction);
        } else if (comboBoxVal.equals(langman.getString(LanguageKey.ZOOM_TARGET_ED))) {
            this.editor.zoom(this.direction);
        } else if (comboBoxVal.equals(langman.getString(LanguageKey.ZOOM_TARGET_EDCO))) {
            this.console.zoomOutputArea(this.direction);
            this.editor.zoom(this.direction);            
        } else if (comboBoxVal.equals(langman.getString(LanguageKey.ZOOM_TARGET_ALL))) {
            this.visualisation.zoom(this.direction);
            this.console.zoomOutputArea(this.direction);
            this.editor.zoom(this.direction);
        }
    }
}
