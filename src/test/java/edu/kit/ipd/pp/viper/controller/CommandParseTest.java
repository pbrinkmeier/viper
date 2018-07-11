package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandParseTest {
    private MainWindow gui;
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.editor = this.gui.getEditorPanel();
        this.console = this.gui.getConsolePanel();
        this.visualisation = this.gui.getVisualisationPanel();
    }

    /**
     * Tests whether an empty program gets parsed correctly.
     */
    @Test
    public void testEmpty() {
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.editor.setSourceText("");
        new CommandParse(this.console, this.editor, this.visualisation, this.gui::switchClickableState).execute();

        assertTrue(this.editor.getSourceText().equals(""));
        assertFalse(this.visualisation.hasGraph());
        assertTrue(this.console.getOutputAreaText().trim()
                .equals(LanguageManager.getInstance().getString(LanguageKey.PARSER_SUCCESS)));
        assertFalse(this.console.hasLockedInput());
    }

    /**
     * Tests whether an incorrect program (like an open parenthesis) gets
     * rejected by the parser.
     */
    @Test
    public void testIncorrectSyntax() {
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.editor.setSourceText("(");
        new CommandParse(this.console, this.editor, this.visualisation, this.gui::switchClickableState).execute();

        assertTrue(this.editor.getSourceText().trim().equals("("));
        assertFalse(this.visualisation.hasGraph());
        assertFalse(this.console.getOutputAreaText().trim()
                .equals(LanguageManager.getInstance().getString(LanguageKey.PARSER_SUCCESS)));
        assertTrue(this.console.hasLockedInput());
    }

    /**
     * Tests whether the simpsons.pl example program gets parsed.
     */
    @Test
    public void testSimpsons() {
        this.console.clearAll();
        this.visualisation.clearVisualization();
        this.editor.setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        new CommandParse(this.console, this.editor, this.visualisation, this.gui::switchClickableState).execute();

        assertTrue(this.editor.getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
        assertFalse(this.visualisation.hasGraph());
        assertTrue(this.console.getOutputAreaText().trim()
                .equals(LanguageManager.getInstance().getString(LanguageKey.PARSER_SUCCESS)));
        assertFalse(this.console.hasLockedInput());
    }
}
