package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.MainWindow;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

/**
 * Command for exporting the visualisation to a supported image format (PNG or
 * SVG).
 */
public class CommandExportImage extends Command {
    private ConsolePanel console;
    private ImageFormat format;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new image export command.
     * 
     * @param console Panel of the console area
     * @param format Format of the image to be saved
     * @param interpreterManager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandExportImage(ConsolePanel console, ImageFormat format, InterpreterManager interpreterManager) {
        this.console = console;
        this.format = format;
        this.interpreterManager = interpreterManager;
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();

        JFileChooser chooser = new JFileChooser();

        if (this.format == ImageFormat.PNG) {
            chooser.setFileFilter(FileFilters.PNG_FILTER);
        } else if (this.format == ImageFormat.SVG) {
            chooser.setFileFilter(FileFilters.SVG_FILTER);
        }

        int rv = chooser.showSaveDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            switch (this.format) {
            case SVG:
                exportSVG(chooser.getSelectedFile());
                break;
            case PNG:
                exportPNG(chooser.getSelectedFile());
                break;
            default:
                String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_UNSUPPORTED_FORMAT);
                this.console.printLine(msg, LogType.ERROR);
            }
        }
    }

    /**
     * SVG export routine. This should only be used internally, but is public for
     * testing purposes.
     * 
     * @param f file to export the SVG graph to
     */
    public void exportSVG(File f) {
        Graphviz viz = Graphviz.fromGraph(this.interpreterManager.getCurrentVisualisation());

        File file = FileUtilities.checkForMissingExtension(f, ".svg");
        try {
            viz.render(Format.SVG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
        } catch (IOException e) {
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.ERROR);

            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
        }
    }

    /**
     * PNG export routine. This should only be used internally, but is public for
     * testing purposes.
     * 
     * @param f file to export the PNG graph to
     */
    public void exportPNG(File f) {
        Graphviz viz = Graphviz.fromGraph(this.interpreterManager.getCurrentVisualisation());

        File file = FileUtilities.checkForMissingExtension(f, ".png");
        try {
            viz.render(Format.PNG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
        } catch (IOException e) {
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.ERROR);

            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
        }
    }
}
