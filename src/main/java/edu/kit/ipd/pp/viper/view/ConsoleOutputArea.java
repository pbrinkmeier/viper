package edu.kit.ipd.pp.viper.view;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Represents a console-like text panel
 */
public class ConsoleOutputArea extends JTextPane {
    /**
     * Initialises the output area
     */
    public ConsoleOutputArea() {
        super();

        this.setEnabled(false);
    }

    /**
     * Clears the entire console
     */
    public void clear() {
        this.setText(null);
    }

    /**
     * Prints a new line (including a line break) to the console. The {@link LogType} determines
     * the line color. {@link LogType#DEBUG} will only be printed if the program was started in
     * debug mode.
     * 
     * @param line The String to print
     * @param type Type of message
     */
    public void printLine(String line, LogType type) {
        Color color;

        switch (type) {
        case SUCCESS:
            color = Color.GREEN;
            break;
        case ERROR:
            color = Color.RED;
            break;
        case DEBUG:
            if (!MainWindow.inDebugMode())
                return;
            color = Color.GRAY;
            break;
        default:
        case INFO:
            color = Color.BLACK;
            break;
        }

        StyleContext context = StyleContext.getDefaultStyleContext();

        // set some attributes for font, alignment etc.
        AttributeSet set = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        set = context.addAttribute(set, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        set = context.addAttribute(set, StyleConstants.FontFamily, "monospace");

        this.setCaretPosition(this.getDocument().getLength());
        this.setCharacterAttributes(set, false);
        this.replaceSelection(line + "\n");
    }

    /**
     * Deprecated method to print to the console, used {@link ConsolePanel#printLine(String, LogType)} instead
     * 
     * @param line  String to print
     * @param color Color of line (IGNORED! Use {@link ConsolePanel#printLine(String, LogType)} instead)
     */
    @Deprecated
    public void printLine(String line, Color color) {
        this.printLine(line, LogType.INFO);
    }
}
