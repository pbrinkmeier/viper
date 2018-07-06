package edu.kit.ipd.pp.viper.controller;

import java.awt.Color;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;

/**
 * Command for formatting the editor content to a standardized format.
 */
public class CommandFormat extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private String source;

    /**
     * Initializes a new format command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the visualisation area
     */
    public CommandFormat(ConsolePanel console, EditorPanel editor) {
        this.console = console;
        this.editor = editor;
        this.source = "";
    }

    /**
     * Executes the command.
     */
    public void execute() {
        boolean changed = false;
        this.source = this.editor.getSourceText();
        changed |= removeUnneccessaryLines();
        changed |= splitMultipleRulesPerLine();
        changed |= alignSubgoals();
        changed |= fixWhitespace();

        if (changed) {
            this.editor.setSourceText(this.source);
            this.editor.setHasChanged(true);
            this.console.printLine(LanguageManager.getInstance().getString(LanguageKey.EDITOR_FORMATTED), Color.BLACK);
        }
    }

    // Single empty lines are kept, multiple empty lines are reduced to a single one
    private boolean removeUnneccessaryLines() {
        String newCode = "";
        final String[] lines = this.source.split(System.getProperty("line.separator"));

        boolean changed = false;
        boolean lastLineWasEmpty = false;
        for (String s : lines) {
            if (s.isEmpty()) {
                if (lastLineWasEmpty) {
                    changed = true;
                } else {
                    newCode += '\n';
                    lastLineWasEmpty = true;
                }
            } else {
                newCode += s + '\n';
                lastLineWasEmpty = false;
            }
        }

        this.source = newCode;
        System.out.println(Boolean.toString(changed));
        return changed;
    }

    // Multiple rules per line are put into separate lines
    private boolean splitMultipleRulesPerLine() {
        String newCode = "";
        final String[] lines = this.source.split(System.getProperty("line.separator"));

        boolean changed = false;
        for (String s : lines) {
            if (s.isEmpty()) {
                newCode += '\n';
            } else {
                final String[] rules = s.split("\\.");
                changed |= rules.length > 1;
                for (int i = 0; i < rules.length; i++) {
                    if (!rules[i].isEmpty()) {
                        if (i == rules.length - 1 && !s.substring(s.length() - 1).equals("."))
                            newCode += rules[i].trim() + "\n";
                        else
                            newCode += rules[i].trim() + ".\n";
                    }
                }
            }
        }

        this.source = newCode;
        return changed;
    }

    // Multiple subgoals are put into separate lines indented with two spaces
    private boolean alignSubgoals() {
        return false;
    }

    // Missing or superfluous whitespace gets repaired
    private boolean fixWhitespace() {
        return false;
    }
}
