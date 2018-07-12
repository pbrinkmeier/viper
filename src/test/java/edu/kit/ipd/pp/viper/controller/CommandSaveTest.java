package edu.kit.ipd.pp.viper.controller;

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
     * Tests saving an unchanged file that has already been written to disk.
     */
    @Test
    public void testEmptySavedFile() {
        File test = new File("testEmpty.pl");
        this.editor.setFileReference(test);
        this.editor.setHasChanged(false);
        new CommandSave(this.console, this.editor, SaveType.SAVE).execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testEmpty.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertTrue(fileContents.trim().equals(editor.getSourceText().trim()));
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
        new CommandSave(this.console, this.editor, SaveType.SAVE).execute();

        assertTrue(test.exists());
        assertTrue(test.getName().equals("testSimpsons.pl"));

        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(test.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertTrue(fileContents.trim().equals(editor.getSourceText().trim()));
        test.delete();
    }
}
