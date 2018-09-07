package edu.kit.ipd.pp.viper.view;

import org.junit.Before;

public class MainWindowTest {
    private MainWindow gui;
    
    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
    }
}
