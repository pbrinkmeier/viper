package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import edu.kit.ipd.pp.viper.model.visualisation.LatexMaker;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;

/**
 * Command for exporting the visualisation to TikZ for LaTex.
 */
public class CommandExportTikz extends Command {
    private ConsolePanel console;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new TikZ export command.
     * 
     * @param console Panel of the console area
     * @param interpreterManager Interpreter manager with a reference to the current
     * interpreter
     */
    public CommandExportTikz(ConsolePanel console, InterpreterManager interpreterManager) {
        this.console = console;
        this.interpreterManager = interpreterManager;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        FileFilter filter = new FileFilter() {
            @Override
            public String getDescription() {
                return LanguageManager.getInstance().getString(LanguageKey.TIKZ_FILES);
            }

            @Override
            public boolean accept(File f) {
                return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("tikz") || f.isDirectory();
            }
        };

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        int rv = chooser.showSaveDialog(null);

        if (rv == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream out = new FileOutputStream(chooser.getSelectedFile());
                final String code = LatexMaker.createLatex(this.interpreterManager.getCurrentState());
                out.write(code.getBytes());
                out.flush();
                out.close();

                String msg = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_SUCCESS);
                console.printLine(msg + ": " + chooser.getSelectedFile().getAbsolutePath(), LogType.INFO);
            } catch (IOException e) {
                String err = LanguageManager.getInstance().getString(LanguageKey.EXPORT_FILE_ERROR);
                this.console.printLine(err + ": " + chooser.getSelectedFile().getAbsolutePath(), LogType.ERROR);
                e.printStackTrace();
            }
        }
    }
}
