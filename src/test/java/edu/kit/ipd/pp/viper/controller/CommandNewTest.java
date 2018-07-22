package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandNewTest {
    private MainWindow gui;
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private InterpreterManager manager;

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
        this.manager = this.gui.getInterpreterManager();
    }

    /**
     * Tests whether the new command actually clears the editor. This test assumes
     * there are no changes left to be saved to disk.
     */
    @Test
    public void getsCleared() {
        this.editor.setSourceText("test");
        this.editor.setHasChanged(false);
        CommandSave save = new CommandSave(this.console, this.editor, SaveType.SAVE, this.gui::setWindowTitle,
                this.manager);
        new CommandNew(this.console, this.editor, this.visualisation,
                this.gui::setTitle,
                this.gui::switchClickableState, save, this.manager).execute();

        assertTrue(this.editor.getSourceText().equals(""));
    }
}
