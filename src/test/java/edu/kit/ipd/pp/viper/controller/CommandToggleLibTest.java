package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandToggleLibTest {
    /**
     * Tests twice whether toggling the standard library works properly.
     */
    @Test
    public void switchOnAndOffTest() {
        MainWindow gui = new MainWindow(false);
        
        boolean firstState = gui.getInterpreterManager().isStandardEnabled();
        new CommandToggleLib(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui.getPreferencesManager(), gui::switchClickableState).execute();
        assertTrue(gui.getInterpreterManager().isStandardEnabled() == !firstState);

        new CommandToggleLib(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui.getPreferencesManager(), gui::switchClickableState).execute();
        assertTrue(gui.getInterpreterManager().isStandardEnabled() == firstState);
    }
}
