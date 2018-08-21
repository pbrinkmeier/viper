package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandSaveTest {
    /**
     * Tests saving an unchanged file that has already been written to disk.
     */
    @Test
    public void testEmptySavedFile() {
        MainWindow gui = new MainWindow(false);
        
        File test = new File("testEmpty.pl");

        gui.getEditorPanel().setFileReference(test);
        gui.getEditorPanel().setHasChanged(false);
        gui.getCommandSave().execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testEmpty.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(fileContents.trim().equals(gui.getEditorPanel().getSourceText().trim()));
        test.delete();
    }

    /**
     * Tests saving an unchanged file that has already been written to disk.
     */
    @Test
    public void testSimpsonsSavedFile() {
        MainWindow gui = new MainWindow(false);
        
        File test = new File("testSimpsons.pl");
        gui.getEditorPanel().setSourceText(SharedTestConstants.SIMPSONS_FORMATTED);
        gui.getEditorPanel().setFileReference(test);
        gui.getEditorPanel().setHasChanged(false);
        gui.getCommandSave().execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testSimpsons.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(fileContents.trim().equals(gui.getEditorPanel().getSourceText().trim()));
        test.delete();
    }

    /**
     * Tests for correct error output.
     */
    @Test
    public void testErrorOutput() {
        MainWindow gui = new MainWindow(false);
        
        final String testPath = "/test/testfile.pl";
        gui.getConsolePanel().clearAll();
        gui.getCommandSave().printSaveError(null, testPath);

        final String expected = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_ERROR) + ": " + testPath;
        assertTrue(gui.getConsolePanel().getOutputAreaText().trim().equals(expected.trim()));
    }

    /**
     * Tests the writing routine.
     */
    @Test
    public void testWrite() {
        MainWindow gui = new MainWindow(false);
        
        File testFile = new File("testfile.pl");
        gui.getConsolePanel().clearAll();
        gui.getCommandSave().writeFile(testFile);

        final String expected = LanguageManager.getInstance().getString(LanguageKey.SAVE_FILE_SUCCESS) + ": "
                + testFile.getAbsolutePath();

        assertTrue(testFile.exists());
        assertFalse(gui.getEditorPanel().hasChanged());
        assertTrue(gui.getEditorPanel().hasFileReference());
        assertTrue(gui.getConsolePanel().getOutputAreaText().trim().equals(expected.trim()));

        testFile.delete();
    }
}
