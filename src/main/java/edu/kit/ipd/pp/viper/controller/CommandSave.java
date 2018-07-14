package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

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
        if (this.saveType == SaveType.SAVE && this.editor.hasFileReference())
            save();
        else
            saveAs();
    }

    /**
     * Save error printing routine. This should only be called internally, but it's
     * public for testing purposes.
     * 
     * @param e IOException caused by failing to write to disk
     * @param filePath the path of the file that caused the exception
     */
    public void printSaveError(IOException e, String filePath) {
        String err = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR);
        this.console.printLine(err + ": " + filePath, LogType.ERROR);

        if (MainWindow.inDebugMode()) {
            e.printStackTrace();
        }
    }

    /**
     * Write-to-disk routine. This should only be used internally, but it's public
     * for testing purposes.
     * 
     * @param file the file to be written
     * @throws IOException when the writing fails
     */
    public void writeFile(File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        out.write(this.editor.getSourceText().getBytes());
        out.flush();
        out.close();
        this.editor.setHasChanged(false);
        this.editor.setFileReference(file);

        String msg = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_SUCCESS);
        this.console.printLine(msg + ": " + file.getAbsolutePath(), LogType.INFO);
    }

    private void save() {
        File file = this.editor.getFileReference();
        try {
            writeFile(file);
        } catch (IOException e) {
            printSaveError(e, file.getAbsolutePath());
        }
    }

    private void saveAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(FileFilters.PL_FILTER);
        int rv = chooser.showSaveDialog(null);

        if (rv == JFileChooser.APPROVE_OPTION) {
            File file = FileUtilities.checkForMissingExtension(chooser.getSelectedFile(), ".pl");
            try {
                writeFile(file);
            } catch (IOException e) {
                printSaveError(e, file.getAbsolutePath());
            }
        }
    }
}
