package edu.kit.ipd.pp.viper.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * Represents an item that can be added to a {@link Menu}.
 */
public class MenuItem extends JMenuItem implements ActionListener, Observer {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -3678608047830149856L;

    /**
     * The key used for translation
     */
    private final LanguageKey textKey;

    /**
     * Command to execute
     */
    private final Command command;

    /**
     * Creates a new menu item that can be used to execute a specific action,
     * realized through a {@link Command}.
     * 
     * @param textKey Key used for translation
     * @param command Command to execute when item was checked/unchecked
     * @param shortcut An optional keyboard shortcut (null is fine too)
     */
    public MenuItem(LanguageKey textKey, Command command, KeyboardShortcut shortcut) {
        super(LanguageManager.getInstance().getString(textKey));

        LanguageManager.getInstance().addObserver(this);

        this.textKey = textKey;
        this.command = command;

        this.addActionListener(this);
        
        if (shortcut != null) {
            this.setAccelerator(shortcut.getKeyStroke());
        }
    }

    /**
     * Called when item was checked or unchecked
     * 
     * @param event Event that was performed, ignored here
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        this.command.execute();
    }

    /**
     * Called by the {@link LanguageManager} when the locale was changed. This
     * triggers an update of the item text.
     * 
     * @param obs Class that triggered the update, ignored here
     * @param obj Object that was triggered, ignored here
     */
    @Override
    public void update(Observable obs, Object obj) {
        this.setText(LanguageManager.getInstance().getString(this.textKey));
    }
}
