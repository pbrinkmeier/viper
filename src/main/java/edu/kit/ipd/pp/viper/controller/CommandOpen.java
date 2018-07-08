package edu.kit.ipd.pp.viper.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for opening a file and writing its contents to the editor. This also
 * sets a reference to the file so future saving operations can overwrite this
 * file.
 */
public class CommandOpen extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;

    /**
     * Initializes a new open command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     */
    public CommandOpen(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        FileFilter filter = new FileFilter() {
            @Override
            public String getDescription() {
                return LanguageManager.getInstance().getString(LanguageKey.PROLOG_FILES);
            }

            @Override
            public boolean accept(File f) {
                return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("pl") || f.isDirectory();
            }
        };

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        int rv = chooser.showOpenDialog(null);

        if (rv == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream in = new FileInputStream(chooser.getSelectedFile());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String str = "";
                StringBuffer buf = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buf.append(str + '\n');
                }

                this.editor.setSourceText(buf.toString());
                this.editor.setHasChanged(false);
                this.editor.setFileReference(chooser.getSelectedFile());
                this.visualisation.clearVisualization();

                final String out = LanguageManager.getInstance().getString(LanguageKey.OPEN_FILE_SUCCESS);
                this.console.clearAll();
                this.console.printLine(out + ": " + chooser.getSelectedFile().getAbsolutePath(), LogType.INFO);

                in.close();
                reader.close();
            } catch (IOException e) {
                String err = LanguageManager.getInstance().getString(LanguageKey.OPEN_FILE_ERROR);
                this.console.printLine(err + ": " + chooser.getSelectedFile().getAbsolutePath(), LogType.ERROR);

                if (MainWindow.inDebugMode()) {
                    e.printStackTrace();
                }
                return;
            }

        }
    }
}
