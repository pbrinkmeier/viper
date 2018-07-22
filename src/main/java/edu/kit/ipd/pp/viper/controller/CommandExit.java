package edu.kit.ipd.pp.viper.controller;

import javax.swing.JOptionPane;

import edu.kit.ipd.pp.viper.view.EditorPanel;

/**
 * Command for exiting the program.
 */
public class CommandExit extends Command {
    private EditorPanel editor;
    private final CommandSave commandSave;
    private InterpreterManager interpreterManager;

    /**
     * Initializes a new exit command.
     * 
     * @param editor The Editor window
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     */
    public CommandExit(EditorPanel editor, CommandSave save, InterpreterManager manager) {
        this.editor = editor;
        this.commandSave = save;
        this.interpreterManager = manager;
    }

    @Override
    public void execute() {
        this.interpreterManager.cancel();

        if (this.editor.hasChanged()) {
            LanguageManager langman = LanguageManager.getInstance();
            Object options[] = {langman.getString(LanguageKey.DIALOG_YES), langman.getString(LanguageKey.DIALOG_NO),
                    langman.getString(LanguageKey.DIALOG_CANCEL)};
            final int rv = JOptionPane.showOptionDialog(null, langman.getString(LanguageKey.CONFIRMATION),
                    langman.getString(LanguageKey.CONFIRMATION_CLOSE_TITLE), JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

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
