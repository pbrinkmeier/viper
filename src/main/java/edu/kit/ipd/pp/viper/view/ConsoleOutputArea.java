package edu.kit.ipd.pp.viper.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JTextPane;
import javax.swing.SizeRequirements;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;

import edu.kit.ipd.pp.viper.controller.PreferencesManager;

/**
 * Represents a console-like text panel
 */
public class ConsoleOutputArea extends JTextPane {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -2469735720829687714L;

    private static final int FONT_MIN_SIZE = 10;
    private static final int FONT_MAX_SIZE = 40;

    private final Semaphore mutex = new Semaphore(1);

    private int fontSize;
    private final List<HistoryEntry> history;

    /**
     * The preferences manager coordinating the text size after a restart
     */
    private PreferencesManager preferencesManager;

    /**
     * Initialises the output area
     */
    public ConsoleOutputArea() {
        super();

        this.fontSize = PreferencesManager.DEFAULT_TEXT_SIZE;
        this.history = new ArrayList<HistoryEntry>();
        this.setFont(new Font("Monospaced", Font.PLAIN, this.fontSize));
        this.setEditable(false);
        this.setEditorKit(new CustomEditorKit());
    }

    /**
     * Sets the preferences manager for the console output area.
     * 
     * @param preferencesManager The preferences manager to be set
     */
    public void setPreferencesManager(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        this.fontSize = this.preferencesManager.getTextSize();
    }

    /**
     * Returns the current font size. Only used for testing purposes.
     * 
     * @return the current font size
     */
    public int getFontSize() {
        return this.fontSize;
    }
    
    /**
     * Sets the font size.
     * Only used for testing purposes.
     * 
     * @param fontSize the new font size
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Clears the entire console
     */
    public void clear() {
        this.acquireMutex();

        this.setText("");
        this.history.clear();

        this.releaseMutex();
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
        this.acquireMutex();

        this.history.add(new HistoryEntry(line, type));
        this.updateContent();

        this.releaseMutex();
    }

    private void updateContent() {
        this.setText("");
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
                if (!MainWindow.inDebugMode()) {
                    continue;
                }
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
        if (this.fontSize >= FONT_MAX_SIZE) {
            return;
        }

        this.acquireMutex();

        this.fontSize++;
        this.updateContent();
        this.preferencesManager.setTextSize(this.fontSize);

        this.releaseMutex();
    }

    /**
     * Decreases the font size
     */
    public void decreaseFont() {
        if (this.fontSize <= FONT_MIN_SIZE) {
            return;
        }

        this.acquireMutex();

        this.fontSize--;
        this.updateContent();
        this.preferencesManager.setTextSize(this.fontSize);

        this.releaseMutex();
    }

    /**
     * Resets the font size
     */
    public void resetFont() {
        this.acquireMutex();

        this.fontSize = PreferencesManager.DEFAULT_TEXT_SIZE;
        this.updateContent();
        this.preferencesManager.setTextSize(this.fontSize);

        this.releaseMutex();
    }

    private void acquireMutex() {
        try {
            this.mutex.acquire();
        } catch (InterruptedException e) {
            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
        }
    }

    private void releaseMutex() {
        this.mutex.release();
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

    /**
     * This is a custom toolkit that enables letter wrap in a JTextPane. Since we
     * can't use a JTextArea due to our use of colors, we have to implement wrapping
     * by letter ourselves. Due to the architecture of Swings HTMLEditorToolkit,
     * this view factory has to use instanceof to achieve the desired results in a
     * color-compatible Swing component (e.g. the JTextPane or an JEditorPane). The
     * reason for this is the fact that the super-class create()-method only returns
     * an object of the View class, but we have to consider the actual subclass it's
     * an instance of.
     */
    private class CustomEditorKit extends HTMLEditorKit {
        /**
         * Serial version UID
         */
        private static final long serialVersionUID = 5952888855820746238L;

        @Override
        public ViewFactory getViewFactory() {
            return new HTMLFactory() {
                @Override
                public View create(Element e) {
                    View v = super.create(e);
                    if (v instanceof InlineView) {
                        return new InlineView(e) {
                            @Override
                            public int getBreakWeight(int axis, float pos, float len) {
                                return GoodBreakWeight;
                            }

                            @Override
                            public View breakView(int axis, int p0, float pos, float len) {
                                if (axis == View.X_AXIS) {
                                    checkPainter();
                                    int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
                                    if (p0 == getStartOffset() && p1 == getEndOffset()) {
                                        return this;
                                    }
                                    return createFragment(p0, p1);
                                }
                                return this;
                            }
                        };
                    } else if (v instanceof ParagraphView) {
                        return new ParagraphView(e) {
                            @Override
                            protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
                                SizeRequirements rnew = r;

                                if (rnew == null) {
                                    rnew = new SizeRequirements();
                                }
                                float pref = this.layoutPool.getPreferredSpan(axis);
                                float min = this.layoutPool.getMinimumSpan(axis);
                                rnew.minimum = (int) min;
                                rnew.preferred = Math.max(rnew.minimum, (int) pref);
                                rnew.maximum = Integer.MAX_VALUE;
                                rnew.alignment = 0.5f;
                                return rnew;
                            }
                        };
                    }
                    return v;
                }
            };
        }
    };
}
