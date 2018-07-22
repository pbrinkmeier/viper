package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandFormatTest {
    private MainWindow gui;
    private ConsolePanel console;
    private EditorPanel editor;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.editor = this.gui.getEditorPanel();
        this.console = this.gui.getConsolePanel();
    }

    /**
     * Tests whether the simpsons.pl example gets formatted properly.
     */
    @Test
    public void testSimpsons() {
        this.editor.setSourceText(SharedTestConstants.SIMPSONS_UNFORMATTED);
        new CommandFormat(this.console, this.editor).execute();
        assertTrue(this.editor.getSourceText().equals(SharedTestConstants.SIMPSONS_FORMATTED));
    }
}
