package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

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
}
