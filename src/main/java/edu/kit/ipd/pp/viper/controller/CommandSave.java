package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.MainWindow;

/**
 * Command for saving the editor content to disk as a Prolog file.
 */
public class CommandSave extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private SaveType saveType;
    private Consumer<String> setTitle;

    /**
     * Initializes a new save command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param saveType Save type (either save or save as new)
     * @param setTitle Consumer function to set the window title
     */
    public CommandSave(ConsolePanel console, EditorPanel editor, SaveType saveType, Consumer<String> setTitle) {
        this.console = console;
        this.editor = editor;
        this.saveType = saveType;
        this.setTitle = setTitle;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        if (this.saveType == SaveType.SAVE && this.editor.hasFileReference())
            save();
        else
            saveAs();
    }

    private void save() {
        File file = this.editor.getFileReference();
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(this.editor.getSourceText().getBytes());
            out.flush();
            out.close();
            this.editor.setHasChanged(false);
            this.setTitle.accept(file.getAbsolutePath());
        } catch (IOException e) {
            String err = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR);
            this.console.printLine(err + ": " + file.getAbsolutePath(), LogType.ERROR);

            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
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
            File file = chooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".pl")) {
                file = new File(filePath + ".pl");
            }

            try {
                FileOutputStream out = new FileOutputStream(file);

                out.write(this.editor.getSourceText().getBytes());
                out.flush();
                out.close();
                this.editor.setHasChanged(false);
                this.editor.setFileReference(file);
                this.setTitle.accept(chooser.getSelectedFile().getAbsolutePath());

                String msg = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_SUCCESS);
                this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
            } catch (IOException e) {
                String err = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR);
                this.console.printLine(err + ": " + file.getAbsolutePath(), LogType.ERROR);

                if (MainWindow.inDebugMode()) {
                    e.printStackTrace();
                }
            }
        }
    }
}
