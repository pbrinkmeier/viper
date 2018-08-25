package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class CommandSaveTest extends ControllerTest {
    /**
     * Tests saving an unchanged file that has already been written to disk.
     */
    @Test
    public void emptySavedFileTest() {
        File test = new File("testEmpty.pl");

        this.gui.getEditorPanel().setFileReference(test);
        this.gui.getEditorPanel().setHasChanged(false);
        this.gui.getCommandSave().execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testEmpty.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(fileContents.trim().equals(this.gui.getEditorPanel().getSourceText().trim()));
        test.delete();
    }

    /**
     * Tests saving an unchanged file that has already been written to disk.
     */
    @Test
    public void simpsonsSavedFileTest() {
        File test = new File("testSimpsons.pl");
        this.gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        this.gui.getEditorPanel().setFileReference(test);
        this.gui.getEditorPanel().setHasChanged(false);
        this.gui.getCommandSave().execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testSimpsons.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(fileContents.trim().equals(this.gui.getEditorPanel().getSourceText().trim()));
        test.delete();
    }

    /**
     * Tests for correct error output.
     */
    @Test
    public void errorOutputTest() {
        final String testPath = "/test/testfile.pl";
        this.gui.getConsolePanel().clearAll();
        this.gui.getCommandSave().printSaveError(null, testPath);

        final String expected = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR) + ": " + testPath;
        assertTrue(this.gui.getConsolePanel().getOutputAreaText().trim().equals(expected.trim()));
    }

    /**
     * Tests the writing routine.
     */
    @Test
    public void writeTest() {
        File testFile = new File("testfile.pl");
        this.gui.getConsolePanel().clearAll();
        this.gui.getCommandSave().writeFile(testFile);

        final String expected = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_SUCCESS) + ": "
                + testFile.getAbsolutePath();

        assertTrue(testFile.exists());
        assertFalse(this.gui.getEditorPanel().hasChanged());
        assertTrue(this.gui.getEditorPanel().hasFileReference());
        assertTrue(this.gui.getConsolePanel().getOutputAreaText().trim().equals(expected.trim()));

        testFile.delete();
    }
}
