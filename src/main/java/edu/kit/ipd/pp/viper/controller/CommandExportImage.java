package edu.kit.ipd.pp.viper.controller;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
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
        FileFilter filter = new FileFilter() {
            @Override
            public String getDescription() {
                return LanguageManager.getInstance().getString(LanguageKey.IMAGE_FILES);
            }

            @Override
            public boolean accept(File f) {
                return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("png")
                        || FilenameUtils.getExtension(f.getName()).toLowerCase().equals("svg") || f.isDirectory();
            }
        };

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        int rv = chooser.showSaveDialog(null);

        if (rv == JFileChooser.APPROVE_OPTION) {
            Graph graph = GraphvizMaker.createGraph(interpreterManager.getCurrentState());
            Graphviz viz = Graphviz.fromGraph(graph);

            switch (format) {
            case SVG:
                exportSVG(viz, chooser.getSelectedFile());
                break;
            case PNG:
                exportPNG(viz, chooser.getSelectedFile());
                break;
            default:
                String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_UNSUPPORTED_FORMAT);
                console.printLine(msg, Color.RED);
            }
        }
    }

    private void exportSVG(Graphviz viz, File file) {
        try {
            viz.render(Format.SVG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            console.printLine(msg + ": " + file.getAbsolutePath(), Color.BLACK);
        } catch (IOException e) {
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
            console.printLine(msg + ": " + file.getAbsolutePath(), Color.RED);
            e.printStackTrace();
        }
    }

    private void exportPNG(Graphviz viz, File file) {
        try {
            viz.render(Format.PNG).toFile(file);
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
            console.printLine(msg + ": " + file.getAbsolutePath(), Color.BLACK);
        } catch (IOException e) {
            String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
            console.printLine(msg + ": " + file.getAbsolutePath(), Color.RED);
            e.printStackTrace();
        }
    }
}