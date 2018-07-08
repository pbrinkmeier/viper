package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.MainWindow;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

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
     * interpreter
     */
    public CommandExportImage(ConsolePanel console, ImageFormat format, InterpreterManager interpreterManager) {
        this.console = console;
        this.format = format;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        FileFilter pngfilter = new FileFilter() {
            @Override
            public String getDescription() {
                return LanguageManager.getInstance().getString(LanguageKey.PNG_FILES);
            }

            @Override
            public boolean accept(File f) {
                return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("png") || f.isDirectory();
            }
        };

        FileFilter svgfilter = new FileFilter() {
            @Override
            public String getDescription() {
                return LanguageManager.getInstance().getString(LanguageKey.SVG_FILES);
            }

            @Override
            public boolean accept(File f) {
                return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("svg") || f.isDirectory();
            }
        };

        JFileChooser chooser = new JFileChooser();

        if (format == ImageFormat.PNG) {
            chooser.setFileFilter(pngfilter);
        } else if (format == ImageFormat.SVG) {
            chooser.setFileFilter(svgfilter);
        }

        int rv = chooser.showSaveDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            Graph graph = GraphvizMaker.createGraph(this.interpreterManager.getCurrentState());
            Graphviz viz = Graphviz.fromGraph(graph);

            switch (this.format) {
            case SVG:
                exportSVG(viz, chooser.getSelectedFile());
                break;
            case PNG:
                exportPNG(viz, chooser.getSelectedFile());
                break;
            default:
                String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_UNSUPPORTED_FORMAT);
                console.printLine(msg, LogType.ERROR);
            }
        }
    }

    private void exportSVG(Graphviz viz, File f) {
        File file = f;
        String filePath = file.getAbsolutePath();
        if (!filePath.endsWith(".svg")) {
            file = new File(filePath + ".svg");
        }

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

    private void exportPNG(Graphviz viz, File f) {
        File file = f;
        String filePath = file.getAbsolutePath();
        if (!filePath.endsWith(".png")) {
            file = new File(filePath + ".png");
        }

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