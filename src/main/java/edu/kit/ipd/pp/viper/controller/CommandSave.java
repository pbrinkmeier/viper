package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

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
    private InterpreterManager interpreterManager;
    private FileChooser fileChooser;

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
        this(console, editor, saveType, setTitle, manager, new DefaultFileChooser());
    }

    /**
     * Initializes a new save command. With this one, the file chooser dialog shown can be chosen.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param saveType Save type (either save or save as new)
     * @param setTitle Consumer function to set the window title
     * @param manager The InterpreterManager instance
     * @param fileChooser The file chooser to be used
     */
    public CommandSave(ConsolePanel console, EditorPanel editor, SaveType saveType, Consumer<String> setTitle,
            InterpreterManager manager, FileChooser fileChooser) {
        this.console = console;
        this.editor = editor;
        this.saveType = saveType;
        this.setTitle = setTitle;
        this.interpreterManager = manager;
        this.fileChooser = fileChooser;
    }
    
    @Override
    public void execute() {
        if (this.saveType == SaveType.SAVE && this.editor.hasFileReference())
            this.save();
        else {
            this.interpreterManager.cancel();
            this.saveAs();
        }
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
     */
    public void writeFile(File file) {
        try (FileOutputStream out = new FileOutputStream(file);) {
            out.write(this.editor.getSourceText().getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            this.printSaveError(e, file.getAbsolutePath());
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
        File f = this.fileChooser.showSaveDialog(FileFilters.PL_FILTER);

        if (f != null) {
            File file = FileUtilities.checkForMissingExtension(f, ".pl");
            this.writeFile(file);
        }
    }
}
