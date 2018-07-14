package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.kit.ipd.pp.viper.controller.CommandParseQuery;

/**
 * Represents a panel containing a console-like output field, as well as an
 * input field for prolog queries.
 */
public class ConsolePanel extends JPanel implements HasClickable {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -533194077782445895L;

    /**
     * Main window reference
     */
    private MainWindow main;

    /**
     * Scroll pane
     */
    private JScrollPane scrollPane;

    /**
     * Console panel
     */
    private ConsoleOutputArea outputArea;

    /**
     * Input field panel
     */
    private ConsoleInputField inputField;

    /**
     * Creates a new console panel
     * 
     * @param gui Main window reference
     */
    public ConsolePanel(MainWindow gui) {
        super();

        this.main = gui;

        this.setPreferredSize(new Dimension(400, 200));
        this.setLayout(new BorderLayout());

        this.outputArea = new ConsoleOutputArea();
        this.scrollPane = new JScrollPane(this.outputArea);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setViewportView(this.outputArea);

        this.inputField = new ConsoleInputField(new CommandParseQuery(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel(), this.main.getInterpreterManager(),
                this.main::switchClickableState));

        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.inputField, BorderLayout.PAGE_END);
    }

    /**
     * Clears the console and the input field
     */
    public void clearAll() {
        this.outputArea.clear();
        this.inputField.clear();
    }

    /**
     * Returns the content of the input field
     * 
     * @return String Input field contents
     */
    public String getText() {
        return this.inputField.getText();
    }

    /**
     * Makes the input field un-editable
     */
    public void lockInput() {
        this.inputField.lock();
    }

    /**
     * Makes the input field editable
     */
    public void unlockInput() {
        this.inputField.unlock();
    }

    /**
     * Clears the input field
     */
    public void clearInputField() {
        this.inputField.clear();
    }

    /**
     * Clears the console
     */
    public void clearOutputArea() {
        this.outputArea.clear();
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
        this.outputArea.printLine(line, type);

        JScrollBar bar = this.scrollPane.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    }

    /**
     * Called when program switched to a new state (program was parsed, query was send, ...)
     */
    @Override
    public void switchClickableState(ClickableState state) {
        this.inputField.switchClickableState(state);
    }
}
