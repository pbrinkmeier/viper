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
     * Tests the writing routine.
     */
    @Test
    public void writeTest() {
        File testFile = new File("testfile.pl");
        this.gui.getConsolePanel().clearAll();
        this.gui.getCommandSave().writeFile(testFile);

        assertTrue(testFile.exists());
        assertFalse(this.gui.getEditorPanel().hasChanged());
        assertTrue(this.gui.getEditorPanel().hasFileReference());

        testFile.delete();
    }
    
    /**
     * Tests saving a file by dialog.
     */
    @Test
    public void dialogTest() {
        File file = new File("test.pl");
        final String sourceText = "test";
        
        this.gui.getEditorPanel().setSourceText(sourceText);
        new CommandSave(this.gui.getConsolePanel(), this.gui.getEditorPanel(), SaveType.SAVE_AS,
                this.gui::setTitle, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(file)).execute();
        
        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertTrue(file.exists());
        assertTrue(fileContents.equals(sourceText));
        assertFalse(this.gui.getEditorPanel().hasChanged());
        assertTrue(this.gui.getEditorPanel().hasFileReference());
        
        file.delete();
        
        new CommandSave(this.gui.getConsolePanel(), this.gui.getEditorPanel(), SaveType.SAVE_AS,
                this.gui::setTitle, this.gui.getInterpreterManager(),
                new PreselectionFileChooser(null)).execute();
    }
    
    /**
     * Tests the error output of the command.
     */
    @Test
    public void errorOutputTest() {
        final String testPath = "/test/test.pl";
        final IOException exception = new IOException("Test");

        this.gui.setDebugMode(false);
        this.gui.getConsolePanel().clearAll();
        this.gui.getCommandSave().printSaveError(exception, testPath);
        assertFalse(this.gui.getConsolePanel().getOutputAreaText().isEmpty());

        this.gui.setDebugMode(true);
        this.gui.getConsolePanel().clearAll();
        this.gui.getCommandSave().printSaveError(exception, testPath);
        assertFalse(this.gui.getConsolePanel().getOutputAreaText().isEmpty());        
    }
}
