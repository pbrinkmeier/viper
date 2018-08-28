package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

/**
 * Command for exporting the visualisation to a supported image format (PNG or
 * SVG).
 */
public class CommandExportImage extends Command {
    private final ConsolePanel console;
    private final ImageFormat format;
    private final InterpreterManager manager;

    /**
     * Initializes a new image export command.
     * 
     * @param console Panel of the console area
     * @param format Format of the image to be saved
     * @param manager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandExportImage(ConsolePanel console, ImageFormat format, InterpreterManager manager) {
        super();

        this.console = console;
        this.format = format;
        this.manager = manager;
    }

    @Override
    public void execute() {
        this.manager.cancel();

        JFileChooser chooser = new JFileChooser();

        if (this.format == ImageFormat.PNG) {
            chooser.setFileFilter(FileFilters.PNG_FILTER);
        } else if (this.format == ImageFormat.SVG) {
            chooser.setFileFilter(FileFilters.SVG_FILTER);
        }

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            switch (this.format) {
            case SVG:
                this.exportSVG(chooser.getSelectedFile());
                break;
            case PNG:
                this.exportPNG(chooser.getSelectedFile());
                break;
            default:
                String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_UNSUPPORTED_FORMAT);
                this.console.printLine(msg, LogType.ERROR);
                break;
            }
        }
    }

    /**
     * SVG export routine. This should only be used internally, but is public for
     * testing purposes.
     * 
     * @param name file to export the SVG graph to
     */
    public void exportSVG(File name) {
        Graphviz viz = Graphviz.fromGraph(this.manager.getCurrentVisualisation());

        File file = FileUtilities.checkForMissingExtension(name, ".svg");
        try {
            viz.render(Format.SVG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
        } catch (IOException e) {
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.ERROR);
        }
    }

    /**
     * PNG export routine. This should only be used internally, but is public for
     * testing purposes.
     * 
     * @param name file to export the PNG graph to
     */
    public void exportPNG(File name) {
        Graphviz viz = Graphviz.fromGraph(this.manager.getCurrentVisualisation());

        File file = FileUtilities.checkForMissingExtension(name, ".png");
        try {
            viz.render(Format.PNG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
        } catch (IOException e) {
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.ERROR);
        }
    }
}
