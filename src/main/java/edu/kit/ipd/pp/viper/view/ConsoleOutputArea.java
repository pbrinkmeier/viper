package edu.kit.ipd.pp.viper.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;

/**
 * Represents a console-like text panel
 */
public class ConsoleOutputArea extends JTextPane {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -2469735720829687714L;

    public static final int FONT_DEFAULT_SIZE = 14;
    private static final int FONT_MIN_SIZE = 10;
    private static final int FONT_MAX_SIZE = 40;

    private int fontSize;
    private ArrayList<HistoryEntry> history;
    
    /**
     * The preferences manager coordinating the text size after a restart
     */
    private PreferencesManager preferencesManager;
    
    /**
     * Initialises the output area
     */
    public ConsoleOutputArea() {
        super();

        this.fontSize = FONT_DEFAULT_SIZE;
        this.history = new ArrayList<HistoryEntry>();
        this.setFont(new Font("Monospaced", Font.PLAIN, this.fontSize));
        this.setEditable(false);
    }
    
    /**
     * Sets the preferences manager for the console output area.
     * This can't be done in the constructor since there is a cyclic dependency
     * between the console and the preferences manager. This should be called
     * directly after the init of the preferences manager.
     * 
     * @param preferencesManager The preferences manager to be set
     */
    public void setPreferencesManager(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        this.fontSize = this.preferencesManager.getConsoleTextSize();
    }

    /**
     * Clears the entire console
     */
    public void clear() {
        this.setText(null);
        this.history.clear();
    }

    /**
     * Prints a new line (including a line break) to the console. The
     * {@link LogType} determines the line color. {@link LogType#DEBUG} will only be
     * printed if the program was started in debug mode.
     * 
     * @param line The String to print
     * @param type Type of message
     */
    public void printLine(String line, LogType type) {
        this.history.add(new HistoryEntry(line, type));
        this.updateContent();
    }
    
    private void updateContent() {
        this.setText(null);
        this.setFont(new Font("Monospaced", Font.PLAIN, this.fontSize));
        this.setEditable(true);
        
        for (final HistoryEntry entry : this.history) {
            Color color = ColorScheme.CONSOLE_BLACK;
            switch (entry.getType()) {
            case SUCCESS:
                color = ColorScheme.CONSOLE_GREEN;
                break;
            case ERROR:
                color = ColorScheme.CONSOLE_RED;
                break;
            case DEBUG:
                if (!MainWindow.inDebugMode())
                    continue;
                color = ColorScheme.CONSOLE_GRAY;
                break;
            case INFO:
            default:
                color = ColorScheme.CONSOLE_BLACK;
                break;
            }
            
            StyleContext context = StyleContext.getDefaultStyleContext();
            AttributeSet set = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
            set = context.addAttribute(set, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
            set = context.addAttribute(set, StyleConstants.FontFamily, "monospaced");
            set = context.addAttribute(set, StyleConstants.FontSize, this.fontSize);

            this.setCaretPosition(this.getDocument().getLength());
            this.setCharacterAttributes(set, false);
            this.replaceSelection(entry.getLine() + "\n");
        }
        
        this.setEditable(false);
    }
    
    /**
     * Increases the font size
     */
    public void increaseFont() {
        if (this.fontSize > FONT_MAX_SIZE)
            return;

        this.fontSize++;
        this.updateContent();
        this.preferencesManager.setConsoleTextSize(this.fontSize);
    }

    /**
     * Decreases the font size
     */
    public void decreaseFont() {
        if (this.fontSize < FONT_MIN_SIZE)
            return;

        this.fontSize--;
        this.updateContent();
        this.preferencesManager.setConsoleTextSize(this.fontSize);
    }
    
    public void resetFont() {
        this.fontSize = FONT_DEFAULT_SIZE;
        this.updateContent();
        this.preferencesManager.setConsoleTextSize(this.fontSize);
    }
    
    private class HistoryEntry {
        private final String line;
        private final LogType type;
        
        public HistoryEntry(String line, LogType type) {
            this.line = line;
            this.type = type;
        }
        
        public String getLine() {
            return this.line;
        }
        
        public LogType getType() {
            return this.type;
        }
    }
}
