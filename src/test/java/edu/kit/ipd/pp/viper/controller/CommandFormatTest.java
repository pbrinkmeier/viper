package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandFormatTest {
    private final String unformatted = "father(abe, homer). father(homer, bart).\n" + 
            "father(homer, lisa). mother(marge, bart).\n\n" + 
            "grandfather(X, Y) :-\n" +
            "father(X, Z), parent(Z, Y).\n\n" +
            "parent(X, Y) :- mother(X, Y).\n\n" +
            "parent(X, Y) :- father(X, Y).";
    
    private final String formatted = "father(abe, homer).\n" + 
            "father(homer, bart).\n" + 
            "father(homer, lisa).\n" + 
            "mother(marge, bart).\n\n" + 
            "grandfather(X, Y) :-\n" + 
            "  father(X, Z),\n" + 
            "  parent(Z, Y).\n\n" + 
            "parent(X, Y) :-\n" + 
            "  mother(X, Y).\n\n" + 
            "parent(X, Y) :-\n" + 
            "  father(X, Y).\n";
    
    @Test
    public void testSimpsons() {
        MainWindow gui = new MainWindow(false);
        gui.setVisible(false);
        ConsolePanel console = new ConsolePanel(gui);
        EditorPanel editor = new EditorPanel();
        
        editor.setSourceText(unformatted);
        new CommandFormat(console, editor).execute();
        assertTrue(editor.getSourceText().equals(formatted));
    }
}
