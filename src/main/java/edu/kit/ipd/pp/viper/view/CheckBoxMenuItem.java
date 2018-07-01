package edu.kit.ipd.pp.viper.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

public class CheckBoxMenuItem extends JCheckBoxMenuItem implements ActionListener, Observer {
    private String textKey;
    private Command command;

    /**
     * @param textKey 
     * @param command 
     */
    public CheckBoxMenuItem(String textKey, Command command) {
        super(LanguageManager.getInstance().getString(textKey));
        
        LanguageManager.getInstance().addObserver(this);
        
        this.textKey = textKey;
        this.command = command;
        
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("executing command...");
        this.command.execute();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setText(LanguageManager.getInstance().getString(this.textKey));
    }
}
