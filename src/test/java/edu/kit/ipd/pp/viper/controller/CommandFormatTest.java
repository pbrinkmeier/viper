package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandFormatTest extends ControllerTest {
    /**
     * Tests whether the simpsons.pl example gets formatted properly.
     */
    @Test
    public void simpsonsTest() {
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_UNFORMATTED);
        this.gui.getCommandFormat().execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
    }
    
    /**
     * Tests whether formatting simpsons.pl twice yields the same result as formatting it once.
     */
    @Test
    public void simpsonsTwiceTest() {
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_UNFORMATTED);
        this.gui.getCommandFormat().execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
        this.gui.getCommandFormat().execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
    }
    
    /**
     * Tests whether an invalid program gets rejected properly.
     */
    @Test
    public void invalidTest() {
        final String invalidProgram = "(\n\n\n)(";
        
        this.gui.getEditorPanel().setSourceText(invalidProgram);
        this.gui.getCommandFormat().execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().trim().equals(invalidProgram));
    }
    
    /**
     * Tests whether an invalid program gets rejected properly.
     * Debug mode version.
     */
    @Test
    public void invalidDebugTest() {
        this.gui.setDebugMode(true);
        final String invalidProgram = "(\n\n\n)(";
        
        this.gui.getEditorPanel().setSourceText(invalidProgram);
        this.gui.getCommandFormat().execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().trim().equals(invalidProgram));
        this.gui.setDebugMode(false);
    }
    
    /**
     * Tests formatting an empty program.
     */
    @Test
    public void emptyTest() {
        this.gui.getEditorPanel().setSourceText("");
        this.gui.getCommandFormat().execute();
        assertTrue(this.gui.getEditorPanel().getSourceText().trim().equals(""));
    }
}
