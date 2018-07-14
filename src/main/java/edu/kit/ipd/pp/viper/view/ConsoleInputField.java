package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageKey;

/**
 * Represents a panel contaning an input field for prolog queries, as well as a button to send the query.
 */
public class ConsoleInputField extends JPanel implements HasClickable {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 5877670236808229252L;
    /**
     * Input field
     */
    private final JTextField textField;

    private Button buttonSend;
    
    /**
     * Creates a new panel that contains an input field and a button
     * 
     * @param command Command to execute on button click
     */
    public ConsoleInputField(Command command) {
        super();

        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // initialise text field and add listener for [ENTER] key press
        this.textField = new JTextField();
        this.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                command.execute();
            }
        });
        this.textField.setFont(new Font("monospaced", Font.PLAIN, 14));

        JLabel text = new JLabel("?-");
        text.setFont(new Font("monospaced", Font.PLAIN, 14));

        this.buttonSend = new Button(LanguageKey.BUTTON_SEND, command);
        
        this.add(text, BorderLayout.LINE_START);
        this.add(this.textField, BorderLayout.CENTER);
        this.add(this.buttonSend, BorderLayout.LINE_END);

        this.lock();
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
        this.textField.requestFocusInWindow();
    }

    /**
     * Called when a program was parsed or a query was send. Enables or disables the send
     * button based on that.
     * 
     * @param state The new program state
     */
    @Override
    public void switchClickableState(ClickableState state) {
        switch (state) {
            case NOT_PARSED_YET:
                buttonSend.setEnabled(false);
                break;
            case PARSED_PROGRAM:
            case PARSED_QUERY:
                buttonSend.setEnabled(true);
                break;
            default:
                break;
        }
    }
}
