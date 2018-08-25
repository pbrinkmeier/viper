package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandToggleLibTest extends ControllerTest {
    /**
     * Tests twice whether toggling the standard library works properly.
     */
    @Test
    public void switchOnAndOffTest() {
        boolean firstState = this.gui.getInterpreterManager().isStandardEnabled();
        new CommandToggleLib(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui.getPreferencesManager(),
                this.gui::switchClickableState).execute();
        assertTrue(this.gui.getInterpreterManager().isStandardEnabled() == !firstState);

        new CommandToggleLib(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui.getPreferencesManager(),
                this.gui::switchClickableState).execute();
        assertTrue(this.gui.getInterpreterManager().isStandardEnabled() == firstState);
    }
}
