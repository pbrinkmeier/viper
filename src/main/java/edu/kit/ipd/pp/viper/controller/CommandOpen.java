package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import edu.kit.ipd.pp.viper.view.ClickableState;
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
    private final String path;
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private Consumer<ClickableState> toggleStateFunc;
    private Consumer<String> setTitle;
    private CommandSave commandSave;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new open command. A command constructed this way selects the file using a dialog.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param setTitle Consumer function that can change the window title
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *            elements in the GUI
     * @param commandSave Save command in case the currently opened program has been changed
     * @param manager The InterpreterManager instance
     */
    public CommandOpen(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave commandSave,
            InterpreterManager manager) {
        this.path = "";
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
        this.commandSave = commandSave;
        this.interpreterManager = manager;
    }

    /**
     * Initializes a new open command. A command constructed this way tries to open a file from a given path.
     * 
     * @param path The file path to read from
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param setTitle Consumer function that can change the window title
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *            elements in the GUI
     * @param commandSave Save command in case the currently opened program has been changed
     */
    public CommandOpen(String path, ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave commandSave) {
        this.path = path;
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
        this.commandSave = commandSave;
    }
    
    /**
     * File to String reading routine. This should only be called internally, but
     * it's public for testing purposes.
     * 
     * @param file the file to be read
     * @return the file content as a string
     */
    public String getFileText(File file) {
        String source = "";
        try {
            source = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            printOpenError(e, file.getAbsolutePath());
        }
        
        return source;
    }

    /**
     * Reading error output routine. This should only be called internally, but it's
     * public for testing purposes.
     * 
     * @param e the exception that was raised by the reading failure
     * @param filePath the path of the file that couldn't be read
     */
    public void printOpenError(IOException e, String filePath) {
        String err = LanguageManager.getInstance().getString(LanguageKey.OPEN_FILE_ERROR);
        this.console.printLine(err + ": " + filePath, LogType.ERROR);

        if (MainWindow.inDebugMode()) {
            e.printStackTrace();
        }
    }

    /**
     * UI update routine. This should only be called internally, but it's public for
     * testing purposes.
     * 
     * @param file the file that was read
     */
    public void updateUI(File file) {
        this.editor.setSourceText(getFileText(file));
        this.editor.setHasChanged(false);
        this.editor.setFileReference(file);
        this.visualisation.clearVisualization();

        final String out = LanguageManager.getInstance().getString(LanguageKey.OPEN_FILE_SUCCESS);
        this.console.clearAll();
        this.console.printLine(out + ": " + file.getAbsolutePath(), LogType.INFO);

        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);
        this.setTitle.accept(file.getAbsolutePath());
    }
    
    private void handleUnsavedChanges() {
        LanguageManager langman = LanguageManager.getInstance();
        Object options[] = {langman.getString(LanguageKey.DIALOG_YES), langman.getString(LanguageKey.DIALOG_NO),
                langman.getString(LanguageKey.DIALOG_CANCEL)};
        final int rv2 = JOptionPane.showOptionDialog(null, langman.getString(LanguageKey.CONFIRMATION),
                langman.getString(LanguageKey.CONFIRMATION_CLOSE_TITLE), JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

        if (rv2 == 0) {
            this.commandSave.execute();
        }
        if (rv2 == 2) {
            return;
        }
    }

    private void openByDialog() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(FileFilters.PL_FILTER);
        int rv = chooser.showOpenDialog(null);

        if (rv == JFileChooser.APPROVE_OPTION) {
            if (this.editor.hasChanged())
                handleUnsavedChanges();
            updateUI(chooser.getSelectedFile());
        }
    }

    private void openDirectly() {
        if (this.editor.hasChanged())
            handleUnsavedChanges();
        updateUI(new File(this.path));
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();
        if (this.path.isEmpty())
            openByDialog();
        else
            openDirectly();
    }
}
