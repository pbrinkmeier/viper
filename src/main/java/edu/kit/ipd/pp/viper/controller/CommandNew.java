package edu.kit.ipd.pp.viper.controller;

import java.util.function.Consumer;

import javax.swing.JOptionPane;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for setting the editor up with a new file. This clears the editor and
 * removes any reference to previously saved files.
 */
public class CommandNew extends Command {
    private static final int OPTION_SAVE_YES    = 0;
    private static final int OPTION_SAVE_CANCEL = 2;

    private final ConsolePanel console;
    private final EditorPanel editor;
    private final VisualisationPanel visualisation;
    private final Consumer<ClickableState> toggleStateFunc;
    private final Consumer<String> setTitle;
    private final CommandSave commandSave;
    private final InterpreterManager manager;

    /**
     * Initializes a new "new"-command.
     * 
     * @param console Panel of the console area
     * @param visualisation Panel of the visualisation area
     * @param editor Panel of the editor area
     * @param toggleStateFunc Consumer function that switches the state of
     *        clickable elements in the GUI
     * @param setTitle Consumer function to change the window title
     * @param save the CommandSave instance
     * @param manager The InterpreterManager instance
     */
    public CommandNew(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc, CommandSave save,
            InterpreterManager manager) {
        super();

        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
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
                    langman.getString(LanguageKey.CONFIRMATION_NEW_TITLE), JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

            if (option == OPTION_SAVE_YES) {
                this.commandSave.execute();
            } else if (option != OPTION_SAVE_CANCEL) {
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
