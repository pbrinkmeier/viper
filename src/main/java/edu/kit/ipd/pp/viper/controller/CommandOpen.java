package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for opening a file and writing its contents to the editor. This also
 * sets a reference to the file so future saving operations can overwrite this
 * file.
 */
public class CommandOpen extends Command {
    private static final int OPTION_SAVE_YES    = 0;
    private static final int OPTION_SAVE_CANCEL = 2;

    private final String path;
    private final ConsolePanel console;
    private final EditorPanel editor;
    private final VisualisationPanel visualisation;
    private final Consumer<ClickableState> toggleStateFunc;
    private final Consumer<String> setTitle;
    private final CommandSave commandSave;
    private final InterpreterManager manager;
    
    private OptionPane optionPane;
    private FileChooser fileChooser;

    /**
     * Initializes a new open command. A command constructed this way selects the
     * file using a Java file chooser.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param setTitle Consumer function that can change the window title
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     * @param commandSave Save command in case the currently opened program has been
     *        changed
     * @param manager The InterpreterManager instance
     */
    public CommandOpen(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave commandSave,
            InterpreterManager manager) {
        this(console, editor, visualisation, setTitle, toggleStateFunc, commandSave, manager,
                new DefaultOptionPane(), new DefaultFileChooser());
    }
    
    /**
     * Initializes a new open command. A command constructed this way selects the
     * file using a custom dialog and a custom file chooser.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param setTitle Consumer function that can change the window title
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     * @param commandSave Save command in case the currently opened program has been
     *        changed
     * @param manager The InterpreterManager instance
     * @param optionPane The custom option pane to be used
     * @param fileChooser The custom file chooser to be used
     */
    public CommandOpen(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave commandSave,
            InterpreterManager manager, OptionPane optionPane, FileChooser fileChooser) {
        this.path = "";
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
        this.commandSave = commandSave;
        this.manager = manager;
        this.optionPane = optionPane;
        this.fileChooser = fileChooser;
    }

    /**
     * Initializes a new open command. A command constructed this way tries to open
     * a file from a given path.
     * 
     * @param path The file path to read from
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param setTitle Consumer function that can change the window title
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     * @param commandSave Save command in case the currently opened program has been
     *        changed
     * @param manager The InterpreterManager instance
     */
    public CommandOpen(String path, ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave commandSave,
            InterpreterManager manager) {
        this(path, console, editor, visualisation, setTitle, toggleStateFunc, commandSave, manager,
                new DefaultOptionPane());
    }

    /**
     * Initializes a new open command. A command constructed this way tries to open
     * a file from a given path. This one uses a custom dialog for handling unsaved changes.
     * 
     * @param path The file path to read from
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param setTitle Consumer function that can change the window title
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     * @param commandSave Save command in case the currently opened program has been
     *        changed
     * @param manager The InterpreterManager instance
     * @param optionPane The custom option pane to be used
     */
    public CommandOpen(String path, ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave commandSave,
            InterpreterManager manager, OptionPane optionPane) {
        this.path = path;
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
        this.commandSave = commandSave;
        this.manager = manager;
        this.optionPane = optionPane;
        this.fileChooser = new DefaultFileChooser();
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
            this.printOpenError(e, file.getAbsolutePath());
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
    }

    /**
     * UI update routine. This should only be called internally, but it's public for
     * testing purposes.
     * 
     * @param file the file that was read
     */
    public void updateUI(File file) {
        this.editor.setSourceText(this.getFileText(file));
        this.editor.setHasChanged(false);
        this.editor.setFileReference(file);
        this.visualisation.clearVisualization();

        final String out = LanguageManager.getInstance().getString(LanguageKey.OPEN_FILE_SUCCESS);
        this.console.clearAll();
        this.console.printLine(out + ": " + file.getAbsolutePath(), LogType.INFO);

        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);
        this.setTitle.accept(file.getAbsolutePath());
    }

    private boolean handleUnsavedChanges() {
        LanguageManager langman = LanguageManager.getInstance();
        final String message = langman.getString(LanguageKey.CONFIRMATION);
        final String title = langman.getString(LanguageKey.CONFIRMATION_CLOSE_TITLE);
        final int option = this.optionPane.showOptionDialog(message, title);

        if (option == OPTION_SAVE_YES) {
            this.commandSave.execute();
        }
        if (option == OPTION_SAVE_CANCEL) {
            return true;
        }
        
        return false;
    }

    private void openByDialog() {
        File f = this.fileChooser.showSaveDialog(FileFilters.PL_FILTER);

        if (f != null) {
            boolean cancelled = false;
            if (this.editor.hasChanged()) {
                cancelled = this.handleUnsavedChanges();
            }
            
            if (!cancelled) {
                this.updateUI(f);
            }
        }
    }

    private void openDirectly() {
        boolean cancelled = false;
        if (this.editor.hasChanged()) {
            cancelled = this.handleUnsavedChanges();
        }

        if (!cancelled) {
            this.updateUI(new File(this.path));
        }
    }

    @Override
    public void execute() {
        this.manager.cancel();
        if (this.path.isEmpty()) {
            this.openByDialog();
        } else {
            this.openDirectly();
        }
    }
}
