package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandToggleLibTest {
    private MainWindow gui;
    private ConsolePanel console;
    private VisualisationPanel visualisation;
    private InterpreterManager interpreterManager;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.console = this.gui.getConsolePanel();
        this.visualisation = this.gui.getVisualisationPanel();
        this.interpreterManager = this.gui.getInterpreterManager();
    }

    /**
     * Tests twice whether toggling the standard library works properly.
     */
    @Test
    public void switchOnAndOffTest() {
        boolean firstState = interpreterManager.isStandardEnabled();
        new CommandToggleLib(gui, gui::switchClickableState).execute();
        assertTrue(interpreterManager.isStandardEnabled() == !firstState);
        new CommandToggleLib(gui, gui::switchClickableState).execute();
        assertTrue(interpreterManager.isStandardEnabled() == firstState);
    }
}
