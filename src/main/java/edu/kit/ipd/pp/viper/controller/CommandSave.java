package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.JFileChooser;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;

/**
 * Command for saving the editor content to disk as a Prolog file.
 */
public class CommandSave extends Command {
    private final ConsolePanel console;
    private final EditorPanel editor;
    private final SaveType saveType;
    private final Consumer<String> setTitle;
    private final InterpreterManager manager;

    /**
     * Initializes a new save command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param saveType Save type (either save or save as new)
     * @param setTitle Consumer function to set the window title
     * @param manager The InterpreterManager instance
     */
    public CommandSave(ConsolePanel console, EditorPanel editor, SaveType saveType, Consumer<String> setTitle,
            InterpreterManager manager) {
        super();

        this.console = console;
        this.editor = editor;
        this.saveType = saveType;
        this.setTitle = setTitle;
        this.manager = manager;
    }

    @Override
    public void execute() {
        if (this.saveType == SaveType.SAVE && this.editor.hasFileReference()) {
            this.save();
        } else {
            this.manager.cancel();
            this.saveAs();
        }
    }

    /**
     * Save error printing routine. This should only be called internally, but it's
     * public for testing purposes.
     * 
     * @param filePath the path of the file that caused the exception
     */
    public void printSaveError(String filePath) {
        String err = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR);
        this.console.printLine(err + ": " + filePath, LogType.ERROR);
    }

    /**
     * Write-to-disk routine. This should only be used internally, but it's public
     * for testing purposes.
     * 
     * @param file the file to be written
     */
    public void writeFile(File file) {
        try (FileOutputStream out = new FileOutputStream(file);) {
            out.write(this.editor.getSourceText().getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            this.printSaveError(file.getAbsolutePath());
        }
        this.editor.setHasChanged(false);
        this.editor.setFileReference(file);
        this.setTitle.accept(file.getAbsolutePath());

        String msg = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_SUCCESS);
        this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
    }

    private void save() {
        File file = this.editor.getFileReference();
        this.writeFile(file);
    }

    private void saveAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(FileFilters.PL_FILTER);

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = FileUtilities.checkForMissingExtension(chooser.getSelectedFile(), ".pl");
            this.writeFile(file);
        }
    }
}
