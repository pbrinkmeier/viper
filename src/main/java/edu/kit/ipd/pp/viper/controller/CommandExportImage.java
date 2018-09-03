package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.IOException;

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
    private FileChooser fileChooser;

    /**
     * Initializes a new image export command.
     * 
     * @param console Panel of the console area
     * @param format Format of the image to be saved
     * @param interpreterManager Interpreter manager with a reference to the current
     *        interpreter
     */
    public CommandExportImage(ConsolePanel console, ImageFormat format, InterpreterManager interpreterManager) {
        this(console, format, interpreterManager, new DefaultFileChooser());
    }

    /**
     * Initializes a new image export command. With this one, the file chooser dialog shown can be chosen.
     * 
     * @param console Panel of the console area
     * @param format Format of the image to be saved
     * @param interpreterManager Interpreter manager with a reference to the current
     *        interpreter
     * @param fileChooser The file chooser to be used
     */
    public CommandExportImage(ConsolePanel console, ImageFormat format, InterpreterManager interpreterManager,
            FileChooser fileChooser) {
        this.console = console;
        this.format = format;
        this.interpreterManager = interpreterManager;
        this.fileChooser = fileChooser;
    }
    
    @Override
    public void execute() {
        this.interpreterManager.cancel();

        File f = null;
        if (this.format == ImageFormat.PNG) {
            f = this.fileChooser.showSaveDialog(FileFilters.PNG_FILTER);
        } else if (this.format == ImageFormat.SVG) {
            f = this.fileChooser.showSaveDialog(FileFilters.SVG_FILTER);
        }

        if (f != null) {
            switch (this.format) {
            case SVG:
                this.exportSVG(f);
                break;
            case PNG:
                this.exportPNG(f);
                break;
            default:
                String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_UNSUPPORTED_FORMAT);
                this.console.printLine(msg, LogType.ERROR);
            }
        }
    }

    private void exportSVG(File f) {
        Graphviz viz = Graphviz.fromGraph(this.interpreterManager.getCurrentVisualisation());

        File file = FileUtilities.checkForMissingExtension(f, ".svg");
        try {
            viz.render(Format.SVG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
        } catch (IOException e) {
            this.handleIOException(e, file.getAbsolutePath());
        }
    }

    private void exportPNG(File f) {
        Graphviz viz = Graphviz.fromGraph(this.interpreterManager.getCurrentVisualisation());

        File file = FileUtilities.checkForMissingExtension(f, ".png");
        try {
            viz.render(Format.PNG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
        } catch (IOException e) {
            this.handleIOException(e, file.getAbsolutePath());
        }
    }
    
    private void handleIOException(IOException e, String filePath) {
        String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
        this.console.printLine(msg + ": " + filePath, LogType.ERROR);

        if (MainWindow.inDebugMode()) {
            e.printStackTrace();
        }
    }
}
