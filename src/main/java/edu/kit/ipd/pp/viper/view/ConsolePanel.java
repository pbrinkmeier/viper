package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class ConsolePanel extends JPanel {
    private ConsoleOutputArea outputArea;
    private ConsoleInputField inputField;

    /**
     * 
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
     * @return
     */
    public void clearAll() {
        this.outputArea.clear();
        this.inputField.clear();
    }

    /**
     * @return String
     */
    public String getText() {
        return this.inputField.getText();
    }

    /**
     * @return
     */
    public void lockInput() {
        this.inputField.lock();
    }

    /**
     * @return
     */
    public void unlockInput() {
        this.inputField.unlock();
    }

    /**
     * @return
     */
    public void clearInputField() {
        this.inputField.clear();
    }

    /**
     * @return
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

    @Deprecated
    public void printLine(String line, Color color) {
        this.outputArea.printLine(line, LogType.INFO);
    }
}
