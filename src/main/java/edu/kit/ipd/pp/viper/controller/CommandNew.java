package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import javax.swing.JOptionPane;

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
    private Consumer<String> setTitle;

    /**
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param editor Panel of the editor area
     * @param setTitle Consumer function to change the window title
     */
    public CommandNew(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
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

            if (rv != 2) {
                clear();
            }
        } else {
            clear();
        }
    }
    
    private void clear() {
        this.editor.setSourceText("");
        this.editor.setHasChanged(false);
        this.editor.setFileReference(null);
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.setTitle.accept("");
    }
}
