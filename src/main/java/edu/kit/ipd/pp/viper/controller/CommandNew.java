package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for setting the editor up with a new file. This clears the editor and
 * removes any reference to previously saved files.
 */
public class CommandNew extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private Consumer<ClickableState> toggleStateFunc;
    private Consumer<String> setTitle;
    private final CommandSave commandSave;
    private InterpreterManager interpreterManager;
    private OptionPane optionPane;

    /**
     * Initializes a new "new"-command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param editor Panel of the editor area
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     * @param setTitle Consumer function to change the window title
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     */
    public CommandNew(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave save,
            InterpreterManager manager) {
        this(console, editor, visualisation, setTitle, toggleStateFunc, save, manager, new DefaultOptionPane());
    }
    
    /**
     * Initializes a new "new"-command. With this one, the option pane shown can be chosen.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param editor Panel of the editor area
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     * @param setTitle Consumer function to change the window title
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     * @param optionPane The option pane to be used. This allows for mocking the option pane for testing
     */
    public CommandNew(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave save,
            InterpreterManager manager, OptionPane optionPane) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
        this.commandSave = save;
        this.interpreterManager = manager;
        this.optionPane = optionPane;
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();
        if (this.editor.hasChanged()) {
            LanguageManager langman = LanguageManager.getInstance();
            final String message = langman.getString(LanguageKey.CONFIRMATION);
            final String title = langman.getString(LanguageKey.CONFIRMATION_NEW_TITLE);
            final int rv = this.optionPane.showOptionDialog(message, title);

            if (rv == 0) {
                this.commandSave.execute();
            }

            if (rv != 2) {
                this.clear();
            }
        } else {
            this.clear();
        }
    }

    private void clear() {
        this.editor.setSourceText("");
        this.editor.setHasChanged(false);
        this.editor.setFileReference(null);
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);
        this.setTitle.accept("");
    }
}
