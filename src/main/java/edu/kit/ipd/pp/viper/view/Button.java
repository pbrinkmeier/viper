package edu.kit.ipd.pp.viper.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * Represents a {@link Button} with a command to execute on click.
 */
public class Button extends JButton implements ActionListener, Observer {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -862558869728026481L;

    /**
     * Each button is translateable via the {@link LanguageManager}, therefore this translation
     * key is necessary
     */
    private final LanguageKey textKey;

    /**
     * Command to execute when the button is pressed
     */
    private final Command command;

    /**
     * Creates a new button using a language key for translation of the button text, as well as command to execute on
     * click.
     * 
     * @param textKey Key used for translation of the button text
     * @param command Command to execute on click
     */
    public Button(LanguageKey textKey, Command command) {
        super(LanguageManager.getInstance().getString(textKey));

        this.textKey = textKey;
        this.command = command;

        this.addActionListener(this);
        LanguageManager.getInstance().addObserver(this);
    }

    /**
     * Called when button was clicked
     * 
     * @param event Event that was performed, ignored here
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        this.command.execute();
    }

    /**
     * Called by the {@link LanguageManager} when the locale was changed. This triggers an update of the button text.
     * 
     * @param obs Class that triggered the update, ignored here
     * @param obj Object that was triggered, ignored here
     */
    @Override
    public void update(Observable obs, Object obj) {
        this.setText(LanguageManager.getInstance().getString(this.textKey));
    }
}
