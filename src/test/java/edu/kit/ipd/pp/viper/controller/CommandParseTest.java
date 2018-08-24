package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandParseTest {
    /**
     * Tests whether an empty program gets parsed correctly.
     */
    @Test
    public void emptyTest() {
        MainWindow gui = new MainWindow(false);
        
        gui.getConsolePanel().clearAll();
        gui.getVisualisationPanel().clearVisualization();
        gui.getEditorPanel().setSourceText("");
        gui.getCommandParse().execute();

        assertTrue(gui.getEditorPanel().getSourceText().equals(""));
        assertFalse(gui.getConsolePanel().hasLockedInput());
    }

    /**
     * Tests whether an incorrect program (like an open parenthesis) gets rejected
     * by the parser.
     */
    @Test
    public void incorrectSyntaxTest() {
        MainWindow gui = new MainWindow(false);
        
        gui.getConsolePanel().clearAll();
        gui.getVisualisationPanel().clearVisualization();
        gui.getEditorPanel().setSourceText("(");
        gui.getCommandParse().execute();

        assertTrue(gui.getEditorPanel().getSourceText().trim().equals("("));
        assertTrue(gui.getConsolePanel().hasLockedInput());
    }

    /**
     * Tests whether the simpsons.pl example program gets parsed.
     */
    @Test
    public void simpsonsTest() {
        MainWindow gui = new MainWindow(false);
        
        gui.getConsolePanel().clearAll();
        gui.getVisualisationPanel().clearVisualization();
        gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        gui.getCommandParse().execute();
        
        assertTrue(gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
        assertFalse(gui.getConsolePanel().hasLockedInput());
    }
}
