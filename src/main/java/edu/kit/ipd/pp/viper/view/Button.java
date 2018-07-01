package edu.kit.ipd.pp.viper.view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

public class Button extends JButton implements ActionListener, Observer {
    private static final int ICON_WIDTH  = 25;
    private static final int ICON_HEIGHT = 25;

    private final String tooltipKey;
    private final Command command;

    /**
     * @param iconPath 
     * @param tooltipKey 
     * @param command 
     */
    public Button(String iconPath, String tooltipKey, Command command) {
        super();

        this.tooltipKey = tooltipKey;
        this.command = command;

        this.setToolTipText(LanguageManager.getInstance().getString(tooltipKey));
        this.addActionListener(this);
        LanguageManager.getInstance().addObserver(this);

        ImageIcon icon = new ImageIcon(this.getClass().getResource(iconPath));
        icon.setImage(icon.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH));

        if (icon != null)
            this.setIcon(icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.command.execute();
    }

    @Override
    public void update(Observable obs, Object obj) {
        this.setToolTipText(LanguageManager.getInstance().getString(this.tooltipKey));
    }
}
