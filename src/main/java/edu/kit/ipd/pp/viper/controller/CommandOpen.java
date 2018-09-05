package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;

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
    private String path = "";
    private boolean isResource = false;
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private Consumer<ClickableState> toggleStateFunc;
    private Consumer<String> setTitle;
    private CommandSave commandSave;
    private InterpreterManager interpreterManager;
    
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
        this.isResource = false;
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
        this.commandSave = commandSave;
        this.interpreterManager = manager;
        this.optionPane = optionPane;
        this.fileChooser = fileChooser;
    }

    /**
     * Initializes a new open command. A command constructed this way tries to open
     * a file from a given path.
     * 
     * @param path Path to file to open
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
     * a file from a given path.
     * 
     * @param path Path to file to open
     * @param isResource Whether the file is a resource inside the jar or a regular file in the file system
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
    public CommandOpen(String path, boolean isResource, ConsolePanel console, EditorPanel editor,
            VisualisationPanel visualisation, Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc,
            CommandSave commandSave, InterpreterManager manager) {
        this(path, console, editor, visualisation, setTitle, toggleStateFunc, commandSave, manager,
                new DefaultOptionPane());
        this.isResource = isResource;
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
        this.isResource = false;
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
        this.commandSave = commandSave;
        this.interpreterManager = manager;
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
            if (this.isResource) {
                InputStream input = this.getClass().getResourceAsStream("/" + file.getPath());
                source = new String(IOUtils.toByteArray(input));
            } else {
                source = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            }
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
        this.editor.setSourceText(this.getFileText(file));
        this.editor.setHasChanged(false);
        if (!this.isResource) {
            this.editor.setFileReference(file);
        }
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
        final int rv = this.optionPane.showOptionDialog(message, title);

        if (rv == 0) {
            this.commandSave.execute();
        }
        if (rv == 2) {
            return true;
        }
        
        return false;
    }

    private void openByDialog() {
        File f = this.fileChooser.showSaveDialog(FileFilters.PL_FILTER);

        if (f != null) {
            boolean cancelled = false;
            if (this.editor.hasChanged())
                cancelled = this.handleUnsavedChanges();
            
            if (!cancelled)
                this.updateUI(f);
        }
    }

    private void openDirectly() {
        boolean cancelled = false;
        if (this.editor.hasChanged())
            cancelled = this.handleUnsavedChanges();

        if (!cancelled)
            this.updateUI(new File(this.path));
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();
        if (this.path.isEmpty())
            this.openByDialog();
        else
            this.openDirectly();
    }
}
