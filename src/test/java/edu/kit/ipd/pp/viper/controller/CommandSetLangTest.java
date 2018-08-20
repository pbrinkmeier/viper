package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandSetLangTest {
    /**
     * Tests whether setting all supported locales works.
     */
    @Test
    public void testAllLocales() {
        LanguageManager langman = LanguageManager.getInstance();
        for (final Locale l : langman.getSupportedLocales()) {
            langman.setLocale(l);
            assertTrue(l.equals(LanguageManager.getCurrentLocale()));
        }
    }

    private void setupVisualisation(MainWindow gui) {
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(), gui.getInterpreterManager(),
                gui::switchClickableState).execute();
    }
    
    /**
     * Tests setting the language to English.
     */
    @Test
    public void testEnglish() {
        MainWindow gui = new MainWindow(false);
        
        CommandSetLang command = new CommandSetLang(gui.getConsolePanel(), gui.getVisualisationPanel(), Locale.ENGLISH,
                gui.getInterpreterManager(), gui.getPreferencesManager());

        command.execute();
        assertTrue(LanguageManager.getCurrentLocale().equals(Locale.ENGLISH));
        assertTrue(gui.getPreferencesManager().getLanguage().equals(Locale.ENGLISH));
        assertTrue(gui.getConsolePanel().getOutputAreaText().trim().isEmpty());
        
        setupVisualisation(gui);
        command.execute();
        assertTrue(LanguageManager.getCurrentLocale().equals(Locale.ENGLISH));
        assertTrue(gui.getPreferencesManager().getLanguage().equals(Locale.ENGLISH));
        assertTrue(gui.getConsolePanel().getOutputAreaText().trim().isEmpty());
        assertTrue(gui.getVisualisationPanel().hasGraph());
    }
}
