package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageKey;

public class ConsoleInputField extends JPanel {
    private final JTextField textField;

    /**
     * Creates a new panel that contains an input field and a button
     * 
     * @param command Command to execute on button click
     */
    public ConsoleInputField(Command command) {
        super();

        this.setLayout(new BorderLayout());

        // initialise text field and add listener for [ENTER] key press
        this.textField = new JTextField();
        this.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                command.execute();
            }
        });

        this.add(new JLabel("?-"), BorderLayout.LINE_START);
        this.add(this.textField, BorderLayout.CENTER);
        this.add(new Button(LanguageKey.BUTTON_SEND, command), BorderLayout.LINE_END);
    }

    /**
     * Returns the content of the input field
     * 
     * @return Input field content
     */
    public String getText() {
        return this.textField.getText();
    }

    /**
     * Clears the content of the input field
     */
    public void clear() {
        this.textField.setText(null);
    }

    /**
     * Makes the input field un-editable
     */
    public void lock() {
        this.textField.setEditable(false);
    }

    /**
     * Makes the input field editable
     */
    public void unlock() {
        this.textField.setEditable(true);
    }
}
