package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.view.EditorPanel;

/**
 * Command for exiting the program.
 */
public class CommandExit extends Command {
    private EditorPanel editor;
    private final CommandSave commandSave;
    private InterpreterManager interpreterManager;
    private OptionPane optionPane;

    /**
     * Initializes a new exit command.
     * 
     * @param editor The Editor window
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     */
    public CommandExit(EditorPanel editor, CommandSave save, InterpreterManager manager) {
        this(editor, save, manager, new DefaultOptionPane());
    }

    /**
     * Initializes a new exit command. With this one, the option pane shown can be chosen.
     * 
     * @param editor The Editor window
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     * @param optionPane The option pane to be used. This allows for mocking the option pane for testing
     */
    public CommandExit(EditorPanel editor, CommandSave save, InterpreterManager manager, OptionPane optionPane) {
        this.editor = editor;
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
            final String title = langman.getString(LanguageKey.CONFIRMATION_CLOSE_TITLE);
            final int rv = this.optionPane.showOptionDialog(message, title);
            
            if (rv == 0) {
                this.commandSave.execute();
            }
            if (rv == 2) {
                return;
            }
        }

        System.exit(0);
    }
}
