package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageKey;

public class ConsoleInputField extends JPanel {
    private final JTextField textField;

    /**
     * @param command
     */
    public ConsoleInputField(Command command) {
        super();

        this.setLayout(new BorderLayout());

        this.textField = new JTextField();

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
     * @return
     */
    public void clear() {
        this.textField.setText(null);
    }

    /**
     * @return
     */
    public void lock() {
        this.textField.setEditable(false);
    }

    /**
     * @return
     */
    public void unlock() {
        this.textField.setEditable(true);
    }
}
