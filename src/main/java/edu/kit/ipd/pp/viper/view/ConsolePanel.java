package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * Represents a panel containing a console-like output field, as well as an input field for
 * prolog queries.
 */
public class ConsolePanel extends JPanel {
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
     */
    public ConsolePanel() {
        super();

        this.setLayout(new BorderLayout());

        this.outputArea = new ConsoleOutputArea();
        this.inputField = new ConsoleInputField(null);

        this.add(this.outputArea, BorderLayout.CENTER);
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
     * Prints a new line (including a line break) to the console. The {@link LogType} determines
     * the line color. {@link LogType#DEBUG} will only be printed if the program was started in
     * debug mode.
     * 
     * @param line The String to print
     * @param type Type of message
     */
    public void printLine(String line, LogType type) {
        this.outputArea.printLine(line, type);
    }
}
