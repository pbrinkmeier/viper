package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

public class CommandSetLangTest extends ControllerTest {
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
    
    /**
     * Tests setting the language to English.
     */
    @Test
    public void englishTest() {
        CommandSetLang command = new CommandSetLang(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                Locale.ENGLISH, this.gui.getInterpreterManager(), this.gui.getPreferencesManager());

        command.execute();
        assertTrue(LanguageManager.getCurrentLocale().equals(Locale.ENGLISH));
        assertTrue(this.gui.getPreferencesManager().getLanguage().equals(Locale.ENGLISH));
        
        this.gui.getCommandParse().execute();
        this.gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(this.gui.getConsolePanel(), this.gui.getVisualisationPanel(),
                this.gui.getInterpreterManager(), this.gui::switchClickableState).execute();
        
        command.execute();
        assertTrue(LanguageManager.getCurrentLocale().equals(Locale.ENGLISH));
        assertTrue(this.gui.getPreferencesManager().getLanguage().equals(Locale.ENGLISH));
        assertTrue(this.gui.getVisualisationPanel().hasGraph());
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
