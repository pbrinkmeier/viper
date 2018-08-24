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
    public void allLocalesTest() {
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
    public void englishTest() {
        MainWindow gui = new MainWindow(false);
        
        CommandSetLang command = new CommandSetLang(gui.getConsolePanel(), gui.getVisualisationPanel(), Locale.ENGLISH,
                gui.getInterpreterManager(), gui.getPreferencesManager());

        command.execute();
        assertTrue(LanguageManager.getCurrentLocale().equals(Locale.ENGLISH));
        assertTrue(gui.getPreferencesManager().getLanguage().equals(Locale.ENGLISH));
        assertTrue(gui.getConsolePanel().getOutputAreaText().trim().isEmpty());
        
        this.setupVisualisation(gui);
        command.execute();
        assertTrue(LanguageManager.getCurrentLocale().equals(Locale.ENGLISH));
        assertTrue(gui.getPreferencesManager().getLanguage().equals(Locale.ENGLISH));
        assertTrue(gui.getConsolePanel().getOutputAreaText().trim().isEmpty());
        assertTrue(gui.getVisualisationPanel().hasGraph());
    }
    
    /**
     * Tests whether setting an unsupported language gets rejected properly.
     */
    @Test
    public void unsupportedLocaleTest() {
        LanguageManager langman = LanguageManager.getInstance();
        langman.setLocale(Locale.ENGLISH);
        
        // Note: If this ever gets translated to traditional chinese, we'll be mildly annoyed by this test
        // failing, but the odds are stacked in our favor for now.
        langman.setLocale(Locale.TRADITIONAL_CHINESE);
        assertTrue(LanguageManager.getCurrentLocale().equals(Locale.ENGLISH));
    }
}
