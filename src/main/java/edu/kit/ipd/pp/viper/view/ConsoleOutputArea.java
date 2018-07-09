package edu.kit.ipd.pp.viper.view;

import java.awt.Color;
import java.awt.Font;

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
     * Serial UID
     */
    private static final long serialVersionUID = -2469735720829687714L;

    /**
     * Initialises the output area
     */
    public ConsoleOutputArea() {
        super();

        this.setFont(new Font("Monospaced", Font.PLAIN, 14));
        this.setEditable(false);
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

        // using setEnabled(false) grays out the entire console, removing all colors. therefore, we're using
        // setEditable(false). this however not only disables input from the user, but also input from the
        // program itself. to work around this, the text pane is set editable before a print and set un-editable
        // again after a print
        this.setEditable(true);

        StyleContext context = StyleContext.getDefaultStyleContext();

        // set some attributes for font, alignment etc.
        AttributeSet set = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        set = context.addAttribute(set, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        set = context.addAttribute(set, StyleConstants.FontFamily, "monospace");

        this.setCaretPosition(this.getDocument().getLength());
        this.setCharacterAttributes(set, false);
        this.replaceSelection(line + "\n");

        this.setEditable(false);
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
