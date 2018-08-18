package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * Represents a panel contaning an input field for prolog queries, as well as a
 * button to send the query.
 */
public class ConsoleInputField extends JPanel implements KeyListener, HasClickable {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 5877670236808229252L;
    /**
     * Input field
     */
    private final HintedTextField textField;

    private Button buttonSend;
    private List<String> history;
    private int historyPos;

    /**
     * Creates a new panel that contains an input field and a button
     * 
     * @param command Command to execute on button click
     */
    public ConsoleInputField(Command command) {
        super();

        this.history = new ArrayList<>();
        this.historyPos = 0;

        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // initialise text field and add listener for [ENTER] key press
        this.textField = new HintedTextField(LanguageKey.INPUT_HINT);
        this.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                command.execute();
                ConsoleInputField.this.clear();
            }
        });
        
        
        
        this.textField.addKeyListener(this);
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
     * Adds the current input field content to the history
     */
    private void addHistory() {
        String hint = LanguageManager.getInstance().getString(LanguageKey.INPUT_HINT);
        if (!this.textField.getText().equals("")
         && !this.textField.getText().equals(hint)) {
            this.history.add(this.textField.getText());
            this.historyPos = 0;
        }
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
        this.addHistory();
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
     * Called when a program was parsed or a query was send. Enables or disables the
     * send button based on that.
     * 
     * @param state The new program state
     */
    @Override
    public void switchClickableState(ClickableState state) {
        switch (state) {
        case NOT_PARSED_YET:
            this.buttonSend.setEnabled(false);
            this.textField.setEditable(false);
            this.textField.setEnabled(false);
            break;
        case PARSED_PROGRAM:
            this.textField.setEditable(true);
            this.textField.setEnabled(true);
        case PARSED_QUERY:
        case FIRST_STEP:
        case LAST_STEP:
            this.buttonSend.setEnabled(true);
            break;
        case NO_MORE_SOLUTIONS:
            this.buttonSend.setEnabled(true);
            break;
        default:
            break;
        }
    }

    /**
     * Fired when a key was pressed, e.g. an arrow key. Used to "scroll" through the
     * input history
     * 
     * @param event The key event that occured
     */
    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();

        switch (keyCode) {
        case 38: // arrow up key
            if (this.historyPos - 1 >= -this.history.size()) {
                this.historyPos--;
                this.textField.setText(this.history.get(this.history.size() + this.historyPos));
            }

            break;
        case 40: // arrow down key
            if (this.historyPos + 1 < 0) {
                this.historyPos++;
                this.textField.setText(this.history.get(this.history.size() + this.historyPos));
            } else if (this.historyPos + 1 == 0) {
                this.historyPos = 0;
                this.textField.setText(null);
            }

            break;
        default:
            break;
        }
    }

    /**
     * Fired when a key was successfully typed, ignored here
     * 
     * @param e The issued event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Fire when a key was released, ignored here
     * 
     * @param e The issued event
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    private class HintedTextField extends JTextField implements FocusListener, Observer {
        private static final long serialVersionUID = 1805984011473862039L;

        private String hintText;
        private final LanguageKey hint;
        
        public HintedTextField(LanguageKey hint) {
            this.hint = hint;
            this.hintText = "";
            this.addFocusListener(this);
            LanguageManager.getInstance().addObserver(this);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (this.getText().equals(this.hintText)) {
                this.setForeground(ColorScheme.CONSOLE_BLACK);
                this.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (this.getText().isEmpty() && this.isEditable()) {
                this.hintText = LanguageManager.getInstance().getString(this.hint);
                this.setForeground(ColorScheme.PLACEHOLDER_TEXT_COLOR);
                this.setText(this.hintText);
            }
        }

        @Override
        public void update(Observable o, Object arg) {
            if (this.getText().equals(this.hintText) && this.isEditable()) {
                this.setForeground(ColorScheme.PLACEHOLDER_TEXT_COLOR);
                this.hintText = LanguageManager.getInstance().getString(this.hint);
                this.setText(this.hintText);
            }
        }
        
        @Override
        public void setEditable(boolean editable) {
            super.setEditable(editable);
            
            if (!editable) {
                this.setText("");
            }
        }
    }
}
