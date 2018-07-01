package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandNewTest {
    @Test
    public void getsEmptiedTest() {
        ConsolePanel c = new ConsolePanel();
        EditorPanel e = new EditorPanel();
        VisualisationPanel v = new VisualisationPanel();
        CommandNew command = new CommandNew(c, e, v);

        // Fill with content
        c.printLine("Test", Color.BLACK);
        e.setSourceText("Test");
        // TODO: Fill visualisation with graph
        
        command.execute();
        
        // Test if all got cleared
        assertTrue(c.getText().equals(""));
        assertTrue(e.getSourceText().equals(""));
        // TODO: Test if visualisation got cleared
    }
}
