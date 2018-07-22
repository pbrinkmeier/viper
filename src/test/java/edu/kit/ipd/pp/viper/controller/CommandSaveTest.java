package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandSaveTest {
    private MainWindow gui;
    private ConsolePanel console;
    private EditorPanel editor;
    private InterpreterManager manager;
    private CommandSave commandSave;

    /**
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(false);
        this.gui.setVisible(false);
        this.editor = this.gui.getEditorPanel();
        this.console = this.gui.getConsolePanel();
        this.manager = this.gui.getInterpreterManager();
        this.commandSave = new CommandSave(this.console, this.editor, SaveType.SAVE, this.gui::setTitle, this.manager);
    }

    /**
     * Tests saving an unchanged file that has already been written to disk.
     */
    @Test
    public void testEmptySavedFile() {
        File test = new File("testEmpty.pl");
        this.editor.setFileReference(test);
        this.editor.setHasChanged(false);
        this.commandSave.execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testEmpty.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(fileContents.trim().equals(this.editor.getSourceText().trim()));
        test.delete();
    }

    /**
     * Tests saving an unchanged file that has already been written to disk.
     */
    @Test
    public void testSimpsonsSavedFile() {
        File test = new File("testSimpsons.pl");
        this.editor.setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.editor.setFileReference(test);
        this.editor.setHasChanged(false);
        this.commandSave.execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testSimpsons.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(fileContents.trim().equals(this.editor.getSourceText().trim()));
        test.delete();
    }

    /**
     * Tests for correct error output.
     */
    @Test
    public void testErrorOutput() {
        final String testPath = "/test/testfile.pl";
        this.console.clearAll();
        this.commandSave.printSaveError(null, testPath);

        final String expected = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR) + ": " + testPath;
        assertTrue(this.console.getOutputAreaText().trim().equals(expected.trim()));
    }

    /**
     * Tests the writing routine.
     */
    @Test
    public void testWrite() {
        File testFile = new File("testfile.pl");
        this.console.clearAll();
        this.commandSave.writeFile(testFile);

        final String expected = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_SUCCESS) + ": "
                + testFile.getAbsolutePath();

        assertTrue(testFile.exists());
        assertFalse(this.editor.hasChanged());
        assertTrue(this.editor.hasFileReference());
        assertTrue(this.console.getOutputAreaText().trim().equals(expected.trim()));

        testFile.delete();
    }
}
