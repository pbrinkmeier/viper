package edu.kit.ipd.pp.viper.controller;

import java.awt.Color;
import java.util.List;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;

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

    /**
     * Executes the command.
     */
    public void execute() {
        final String source = this.editor.getSourceText();

        List<Rule> rules = null;
        try {
            KnowledgeBase kb = new PrologParser(source).parse();
        
            // TODO: Use the code two lines below once getRules() is made public
            rules = kb.getMatchingRules(null);
            // rules = kb.getRules();

        } catch (ParseException e) {
            this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.PARSER_ERROR), Color.BLACK);
            e.printStackTrace();
        }

        String newSource = "";
        for (final Rule r : rules) {
            List<Goal> subGoals = r.getSubgoals();
            if (subGoals.size() == 0) {
                newSource += r.getHead() + ".\n";
            } else {
                newSource += "\n" + r.getHead() + " :-\n";
                for (int i = 0; i < subGoals.size(); i++) {
                    final Goal g = subGoals.get(i);
                    newSource += "  " + g.toString();
                    newSource += i == subGoals.size() - 1 ? ".\n" : ",\n";
                }
            }
        }

        if (!source.equals(newSource)) {
            this.editor.setSourceText(newSource);
            this.editor.setHasChanged(true);
            this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.EDITOR_FORMATTED), Color.BLACK);
        }
    }
}
