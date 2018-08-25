package edu.kit.ipd.pp.viper.controller;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class ControllerTest {
    /**
     * The used instance of the main window.
     */
    protected MainWindow gui;
    
    /**
     * The instantiation method for the main window.
     * This uses SwingUtilities.invokeAndWait to avoid
     * threading issues with Swing.
     */
    @Before
    public void buildGUI() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    ControllerTest.this.gui = new MainWindow(false);
                }
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * The tear-down method of the GUI. Disposes it to avoid
     * window spamming.
     */
    @After
    public void destroyGUI() {
        this.gui.dispose();
    }
}
