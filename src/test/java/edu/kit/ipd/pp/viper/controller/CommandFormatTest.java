package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandFormatTest {
    /**
     * Tests whether the simpsons.pl example gets formatted properly.
     */
    @Test
    public void testSimpsons() {
        MainWindow gui = new MainWindow(false);
        gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_UNFORMATTED);
        gui.getCommandFormat().execute();
        assertTrue(gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
    }
    
    /**
     * Tests whether formatting simpsons.pl twice yields the same result as formatting it once.
     */
    @Test
    public void testSimpsonsTwice() {
        MainWindow gui = new MainWindow(false);
        gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_UNFORMATTED);
        gui.getCommandFormat().execute();
        assertTrue(gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
        gui.getCommandFormat().execute();
        assertTrue(gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
    }
    
    /**
     * Tests whether an invalid program gets rejected properly.
     */
    @Test
    public void testInvalid() {
        MainWindow gui = new MainWindow(false);
        final String invalidProgram = "(\n\n\n)(";
        
        gui.getEditorPanel().setSourceText(invalidProgram);
        gui.getCommandFormat().execute();
        assertTrue(gui.getEditorPanel().getSourceText().trim().equals(invalidProgram));
    }

    /**
     * Tests whether an invalid program gets rejected properly.
     * Debug mode version.
     */
    @Test
    public void testInvalidDebug() {
        MainWindow gui = new MainWindow(true);
        final String invalidProgram = "(\n\n\n)(";
        
        gui.getEditorPanel().setSourceText(invalidProgram);
        gui.getCommandFormat().execute();
        assertTrue(gui.getEditorPanel().getSourceText().trim().equals(invalidProgram));
    }
    
    /**
     * Tests formatting an empty program.
     */
    @Test
    public void testEmpty() {
        MainWindow gui = new MainWindow(false);
        gui.getEditorPanel().setSourceText("");
        gui.getCommandFormat().execute();
        assertTrue(gui.getEditorPanel().getSourceText().trim().equals(""));
    }
}
