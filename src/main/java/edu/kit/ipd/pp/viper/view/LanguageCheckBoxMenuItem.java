package edu.kit.ipd.pp.viper.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * Represents a checkable menu item used for the language selection.
 */
public class LanguageCheckBoxMenuItem extends JCheckBoxMenuItem implements ActionListener, Observer {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -8680892136901167090L;

    /**
     * The locale of this checkbox
     */
    private final Locale locale;

    /**
     * Command to execute
     */
    private final Command command;

    /**
     * Creates a new checkable menu item that can be used for a "radio selection"
     * using a {@link ButtonGroup} (language selection).
     * 
     * @param locale Locale this checkbox represents
     * @param command Command to execute when item was checked/unchecked
     */
    public LanguageCheckBoxMenuItem(Locale locale, Command command) {
        super(locale.getDisplayLanguage(LanguageManager.getCurrentLocale()));

        LanguageManager.getInstance().addObserver(this);

        this.locale = locale;
        this.command = command;

        this.addActionListener(this);
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
     * Called by the {@link LanguageManager} when the program locale was changed.
     * This triggers an update of the item text.
     * 
     * @param obs Class that triggered the update, ignored here
     * @param obj Object that was triggered, ignored here
     */
    @Override
    public void update(Observable obs, Object obj) {
        this.setText(this.locale.getDisplayLanguage(LanguageManager.getCurrentLocale()));
    }
}
