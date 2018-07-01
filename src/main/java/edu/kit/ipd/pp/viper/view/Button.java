package edu.kit.ipd.pp.viper.view;

import java.net.URL;

import javax.swing.JButton;

import edu.kit.ipd.pp.viper.controller.Command;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

public class Button extends JButton {
    /**
     * @param icon
     * @param tooltipKey
     * @param command
     */
    public Button(String icon, String tooltipKey, Command command) {
        super();
        
        this.setToolTipText(LanguageManager.getInstance().getString(tooltipKey));

        URL iconURL = this.getClass().getResource(icon);
    }
}

/*
 * //Look for the image.
    String imgLocation = "images/"
                         + imageName
                         + ".gif";
    URL imageURL = ToolBarDemo.class.getResource(imgLocation);

    //Create and initialize the button.
    JButton button = new JButton();
    button.setActionCommand(actionCommand);
    button.setToolTipText(toolTipText);
    button.addActionListener(this);

    if (imageURL != null) {                      //image found
        button.setIcon(new ImageIcon(imageURL, altText));
    } else {                                     //no image found
        button.setText(altText);
        System.err.println("Resource not found: " + imgLocation);
    }
 */