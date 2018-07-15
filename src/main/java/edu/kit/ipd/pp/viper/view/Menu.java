package edu.kit.ipd.pp.viper.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * A menu is a wrapper around multiple {@link MenuItem}s or other nested
 * {@link Menu}s.
 */
public class Menu extends JMenu implements Observer {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -9040247902195769299L;

    /**
     * The key used for translation
     */
    private final LanguageKey textKey;

    /**
     * Creates a new menu using a translation key. {@link MenuItem}s can be added
     * using the inherited method {@link JMenu#add(javax.swing.JMenuItem)}
     * 
     * @param textKey Key used for translation
     */
    public Menu(LanguageKey textKey) {
        super(LanguageManager.getInstance().getString(textKey));

        LanguageManager.getInstance().addObserver(this);

        this.textKey = textKey;
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
