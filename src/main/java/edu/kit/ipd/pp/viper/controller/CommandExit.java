package edu.kit.ipd.pp.viper.controller;

import javax.swing.JOptionPane;

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

    /**
     * Initializes a new exit command.
     * 
     * @param editor The Editor window
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     */
    public CommandExit(EditorPanel editor, CommandSave save, InterpreterManager manager) {
        super();

        this.editor = editor;
        this.commandSave = save;
        this.manager = manager;
    }

    @Override
    public void execute() {
        this.manager.cancel();

        if (this.editor.hasChanged()) {
            LanguageManager langman = LanguageManager.getInstance();
            Object options[] = {langman.getString(LanguageKey.DIALOG_YES), langman.getString(LanguageKey.DIALOG_NO),
                    langman.getString(LanguageKey.DIALOG_CANCEL)};
            final int option = JOptionPane.showOptionDialog(null, langman.getString(LanguageKey.CONFIRMATION),
                    langman.getString(LanguageKey.CONFIRMATION_CLOSE_TITLE), JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

            if (option == OPTION_SAVE_YES) {
                this.commandSave.execute();
            } else if (option == OPTION_SAVE_CANCEL) {
                return;
            }
        }

        System.exit(0);
    }
}
