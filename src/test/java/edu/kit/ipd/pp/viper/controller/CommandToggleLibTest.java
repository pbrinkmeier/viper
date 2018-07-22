package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandToggleLibTest {
    private MainWindow gui;
    private InterpreterManager interpreterManager;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.interpreterManager = this.gui.getInterpreterManager();
    }

    /**
     * Tests twice whether toggling the standard library works properly.
     */
    @Test
    public void switchOnAndOffTest() {
        boolean firstState = interpreterManager.isStandardEnabled();
        new CommandToggleLib(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui.getPreferencesManager(),
                gui::switchClickableState).execute();
        assertTrue(interpreterManager.isStandardEnabled() == !firstState);
        new CommandToggleLib(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui.getPreferencesManager(),
                gui::switchClickableState).execute();
        assertTrue(interpreterManager.isStandardEnabled() == firstState);
    }
}
