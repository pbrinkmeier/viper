package edu.kit.ipd.pp.viper.controller;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class ControllerTest {
    protected MainWindow gui;
    
    @Before
    public void buildGUI() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    gui = new MainWindow(false);
                }
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void destroyGUI() {
        this.gui.dispose();
    }
}
