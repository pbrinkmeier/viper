package edu.kit.ipd.pp.viper.view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.CommandSave;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * Represents a {@link ToolBarButton} in the toolbar of the main window. These
 * buttons are shortcuts for functionality also available in the menubar and
 * thus take the same {@link CommandSave}.
 */
public class ToolBarButton extends JButton implements ActionListener, Observer {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -7356326159655702744L;

    /**
     * Dimensions for the icons that are used on all buttons
     */
    private static final int ICON_WIDTH = 25;
    private static final int ICON_HEIGHT = 25;

    /**
     * The tooltip for each button is translateable via the {@link LanguageManager},
     * therefore this translation key is necessary
     */
    private final LanguageKey tooltipKey;

    /**
     * Command to execute when the button is pressed
     */
    private final Command command;

    /**
     * Creates a new button using a path to an icon that will be used for display, a
     * language key for translation of the tooltip, as well as command to execute on
     * click.
     * 
     * @param iconPath Path to the icon that will be displayed on the button
     * @param tooltipKey Key used for translation of the button tooltip
     * @param command Command to execute on click
     */
    public ToolBarButton(String iconPath, LanguageKey tooltipKey, Command command) {
        super();

        this.tooltipKey = tooltipKey;
        this.command = command;

        this.setToolTipText(LanguageManager.getInstance().getString(tooltipKey));
        this.addActionListener(this);
        LanguageManager.getInstance().addObserver(this);

        // load icon and resize it
        ImageIcon icon = new ImageIcon(this.getClass().getResource(iconPath));
        icon.setImage(icon.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH));
        this.setIcon(icon);
    }

    /**
     * Called when button is clicked
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
        this.setToolTipText(LanguageManager.getInstance().getString(this.tooltipKey));
    }
}
