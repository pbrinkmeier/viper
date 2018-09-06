package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandParseTest extends ControllerTest {
    /**
     * Tests whether an empty program gets parsed correctly.
     */
    @Test
    public void emptyTest() {
        this.gui.getConsolePanel().clearAll();
        this.gui.getVisualisationPanel().clearVisualization();
        this.gui.getEditorPanel().setSourceText("");
        this.gui.getCommandParse().execute();

        assertTrue(this.gui.getEditorPanel().getSourceText().equals(""));
        assertFalse(this.gui.getConsolePanel().hasLockedInput());
    }

    /**
     * Tests whether an incorrect program (like an open parenthesis) gets rejected
     * by the parser.
     */
    @Test
    public void incorrectSyntaxTest() {
        this.gui.getConsolePanel().clearAll();
        this.gui.getVisualisationPanel().clearVisualization();
        this.gui.getEditorPanel().setSourceText("(");
        this.gui.getCommandParse().execute();

        assertTrue(this.gui.getEditorPanel().getSourceText().trim().equals("("));
        assertTrue(this.gui.getConsolePanel().hasLockedInput());
    }

    /**
     * Tests whether the simpsons.pl example program gets parsed.
     */
    @Test
    public void simpsonsTest() {
        this.gui.getConsolePanel().clearAll();
        this.gui.getVisualisationPanel().clearVisualization();
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getCommandParse().execute();
        
        assertTrue(this.gui.getEditorPanel().getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
        assertFalse(this.gui.getConsolePanel().hasLockedInput());
    }
    
    /**
     * Tests parsing source code that conflicts with the standard library.
     */
    @Test
    public void conflictingTest() {
        if (!this.gui.getInterpreterManager().isStandardEnabled()) {
            this.gui.getInterpreterManager().toggleStandardLibrary();            
        }
        
        this.gui.getVisualisationPanel().clearVisualization();
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.CONFLICTING_STD_RULE);
        this.gui.getConsolePanel().clearAll();
        this.gui.getCommandParse().execute();
        
        assertFalse(this.gui.getConsolePanel().getOutputAreaText().isEmpty());
    }
}
