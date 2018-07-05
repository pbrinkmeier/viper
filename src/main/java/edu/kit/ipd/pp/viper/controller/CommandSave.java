package edu.kit.ipd.pp.viper.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;

/**
 * Command for saving the editor content to disk as a Prolog file.
 */
public class CommandSave extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private SaveType saveType;

    /**
     * Initializes a new save command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param saveType Save type (either save or save as new)
     */
    public CommandSave(ConsolePanel console, EditorPanel editor, SaveType saveType) {
        this.console = console;
        this.editor = editor;
        this.saveType = saveType;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        if (editor.hasChanged()) {
            if (saveType == SaveType.SAVE && editor.hasFileReference())
                save();
            else
                saveAs();
        }
    }

    private void save() {
        File file = editor.getFileReference();
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(editor.getSourceText().getBytes());
            out.flush();
            out.close();
            editor.setHasChanged(false);
        } catch (IOException e) {
            String err = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR);
            console.printLine(err + ": " + file.getAbsolutePath(), Color.RED);
            e.printStackTrace();
        }
    }

    private void saveAs() {
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
        int rv = chooser.showSaveDialog(null);

        if (rv == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream out = new FileOutputStream(chooser.getSelectedFile());
                out.write(editor.getSourceText().getBytes());
                out.flush();
                out.close();
                editor.setHasChanged(false);
                editor.setFileReference(chooser.getSelectedFile());

                String msg = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_SUCCESS);
                console.printLine(msg + ": " + chooser.getSelectedFile().getAbsolutePath(), Color.BLACK);
            } catch (IOException e) {
                String err = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR);
                console.printLine(err + ": " + chooser.getSelectedFile().getAbsolutePath(), Color.RED);
                e.printStackTrace();
            }
        }
    }
}
