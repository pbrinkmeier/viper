package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandNewTest {
    @Test
    public void getsCleared() {
        MainWindow gui = new MainWindow(false);
        gui.setVisible(false);
        ConsolePanel console = new ConsolePanel(gui);
        EditorPanel editor = new EditorPanel();
        VisualisationPanel visualisation = new VisualisationPanel(gui);
        
        editor.setSourceText("test");
        editor.setHasChanged(false);
        new CommandNew(console, editor, visualisation).execute();

        assertTrue(editor.getSourceText().equals(""));
    }
}
