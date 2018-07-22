package edu.kit.ipd.pp.viper.controller;

import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.MainWindow;

/**
 * Command for formatting the editor content to a standardized format.
 */
public class CommandFormat extends Command {
    private ConsolePanel console;
    private EditorPanel editor;

    /**
     * Initializes a new format command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the visualisation area
     */
    public CommandFormat(ConsolePanel console, EditorPanel editor) {
        this.console = console;
        this.editor = editor;
    }

    @Override
    public void execute() {
        final String source = this.editor.getSourceText();

        KnowledgeBase kb = null;
        try {
            kb = new PrologParser(source).parse();
        } catch (ParseException e) {
            this.console.printLine(
                    LanguageManager.getInstance().getString(LanguageKey.PARSER_ERROR) + "\n" + e.getMessage(),
                    LogType.ERROR);
            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
        }

        if (kb == null)
            return;

        final String newSource = kb.toString();
        if (!source.equals(newSource)) {
            this.editor.setSourceText(newSource);
            this.editor.setHasChanged(true);
            this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.EDITOR_FORMATTED),
                    LogType.INFO);
        } else {
            if (!source.equals(""))
                this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.EDITOR_ALREADY_FORMATTED),
                        LogType.INFO);
        }
    }
}
