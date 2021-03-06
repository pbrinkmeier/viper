package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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

    private final Button buttonSend;
    private final List<String> history;
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
     * Sets custom cursor for all ConsoleInputField subpanels
     * @param cursor cursor to set
     */
    public void setGlobalCursor(Cursor cursor) {
        this.setCursor(cursor);
        this.textField.setCursor(cursor);
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
     * Sets the text of the input field
     * 
     * @param text The text to be set
     */
    public void setText(String text) {
        this.textField.setText(text);
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
            //$FALL-THROUGH$
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
        case KeyEvent.VK_UP: // arrow up key
            if (this.historyPos - 1 >= -this.history.size()) {
                this.historyPos--;
                this.textField.setText(this.history.get(this.history.size() + this.historyPos));
            }

            break;
        case KeyEvent.VK_DOWN: // arrow down key
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
        return;
    }

    /**
     * Fire when a key was released, ignored here
     * 
     * @param e The issued event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }
    
    /**
     * Returns the history of entered queries.
     * Only used for testing.
     * 
     * @return history the history of entered queries
     */
    public List<String> getHistory() {
        return this.history;
    }
    
    /**
     * Clears the history of the input field.
     * Only used for testing.
     */
    public void clearHistory() {
        this.history.clear();
    }
    
    /**
     * Returns if the buttonSend is enabled or not.
     * Only used for testing.
     * 
     * @return boolean The status of buttonSend
     */
    public boolean isButtonSendEnabled() {
        return this.buttonSend.isEnabled();
    }
    
    /**
     * Returns if the textField is enabled or not.
     * Only used for testing.
     * 
     * @return boolean The status of textField
     */
    public boolean isTextFieldEnabled() {
        return this.textField.isEnabled();
    }
    
    /**
     * Returns if the textField is editable or not.
     * Only used for testing.
     * 
     * @return boolean The status of textField
     */
    public boolean isTextFieldEditable() {
        return this.textField.isEditable();
    }
    
    /**
     * Returns this textField-
     * Only used for testing.
     * 
     * @return HintedTextField The text field of this input field
     */
    public HintedTextField getTextField() {
        return this.textField;
    }
    
    /**
     * Sets the foreground color of this textField.
     * Only used for testing.
     * 
     * @param color The new foreground color
     */
    public void setTextFieldForeground(Color color) {
        this.textField.setForeground(color);
    }
    
    /**
     * Gets the foreground color of this textField.
     * Only used for testing.
     * 
     * @return Color The current foreground color
     */
    public Color getTextFieldForeground() {
        return this.textField.getForeground();
    }
    
    /**
     * Calls the focusGained function of the HintedTextField class.
     * Only used for testing.
     */
    public void gainFocus() {
        this.textField.focusGained(null);
    }
    
    /**
     * Calls the focusLost function of the HintedTextField class.
     * Only used for testing.
     */
    public void loseFocus() {
        this.textField.focusLost(null);
    }
    
    private class HintedTextField extends JTextField implements FocusListener, Observer {
        private static final long serialVersionUID = 1805984011473862039L;

        private String hintText;
        private final LanguageKey hint;
        
        public HintedTextField(LanguageKey hint) {
            super();

            this.hint = hint;
            this.hintText = "";
            this.addFocusListener(this);
            LanguageManager.getInstance().addObserver(this);
        }

        @Override
        public void focusGained(FocusEvent e) {
            this.setForeground(ColorScheme.CONSOLE_BLACK);
            if (this.getText().equals(this.hintText)) {
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
