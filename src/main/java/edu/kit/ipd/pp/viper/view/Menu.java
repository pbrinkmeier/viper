package edu.kit.ipd.pp.viper.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

public class Menu extends JMenu implements Observer {
    private String textKey;

    /**
     * @param textKey 
     * @param command
     */
    public Menu(String textKey) {
        super(LanguageManager.getInstance().getString(textKey));
        
        LanguageManager.getInstance().addObserver(this);
        
        this.textKey = textKey;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setText(LanguageManager.getInstance().getString(this.textKey));
    }
}
