package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import javax.swing.JOptionPane;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;

/**
 * Command for exiting the program.
 */
public class CommandExit extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private Consumer<String> setTitle;

    /**
     * Initializes a new exit command.
     * 
     * @param console The output console
     * @param editor The Editor window
     * @param setTitle Consumer function to set the window title
     */
    public CommandExit(ConsolePanel console, EditorPanel editor, Consumer<String> setTitle) {
        this.console = console;
        this.editor = editor;
        this.setTitle = setTitle;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        if (this.editor.hasChanged()) {
            LanguageManager langman = LanguageManager.getInstance();
            Object options[] = {langman.getString(LanguageKey.YES), langman.getString(LanguageKey.NO),
                    langman.getString(LanguageKey.CANCEL)};
            final int rv = JOptionPane.showOptionDialog(null, langman.getString(LanguageKey.CONFIRMATION),
                    langman.getString(LanguageKey.CONFIRMATION_TITLE), JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

            if (rv == 0) {
                CommandSave save = new CommandSave(this.console, this.editor, SaveType.SAVE, this.setTitle);
                save.execute();
            }
            if (rv == 2) {
                return;
            }
        }
        System.exit(0);
    }
}
