package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.EditorPanel;

/**
 * Command for exiting the program.
 */
public class CommandExit extends Command {
    private static final int OPTION_SAVE_YES    = 0;
    private static final int OPTION_SAVE_CANCEL = 2;

    private final EditorPanel editor;
    private final CommandSave commandSave;
    private final InterpreterManager manager;
    private final Runnable disposeOperation;
    private final OptionPane optionPane;

    /**
     * Initializes a new exit command.
     * 
     * @param editor The Editor window
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     * @param disposeOperation The function to dispose the main window
     */
    public CommandExit(EditorPanel editor, CommandSave save, InterpreterManager manager, Runnable disposeOperation) {
        this(editor, save, manager, disposeOperation, new DefaultOptionPane());
    }

    /**
     * Initializes a new exit command. With this one, the option pane shown can be chosen.
     * 
     * @param editor The Editor window
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     * @param optionPane The option pane to be used. This allows for mocking the option pane for testing
     * @param disposeOperation The function to dispose the main window
     */
    public CommandExit(EditorPanel editor, CommandSave save, InterpreterManager manager, Runnable disposeOperation,
            OptionPane optionPane) {
        this.editor = editor;
        this.commandSave = save;
        this.manager = manager;
        this.disposeOperation = disposeOperation;
        this.optionPane = optionPane;
    }
    
    @Override
    public void execute() {
        this.manager.cancel();

        if (this.editor.hasChanged()) {
            LanguageManager langman = LanguageManager.getInstance();
            final String message = langman.getString(LanguageKey.CONFIRMATION);
            final String title = langman.getString(LanguageKey.CONFIRMATION_CLOSE_TITLE);
            final int option = this.optionPane.showOptionDialog(message, title);
            
            if (option == OPTION_SAVE_YES) {
                this.commandSave.execute();
            }
            if (option == OPTION_SAVE_CANCEL) {
                return;
            }
        }

        this.disposeOperation.run();
    }
}
