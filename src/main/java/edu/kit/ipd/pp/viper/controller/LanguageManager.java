package edu.kit.ipd.pp.viper.controller;

import java.util.List;
import java.util.Locale;
import java.util.Observable;

/**
 * Central language manager singleton class. Reads in a list of translations
 * for the supported languages and provides a global interface to set the
 * language and update all relevant GUI components using observation.
 * Internally, a resource bundle is used to provide translated strings for
 * any text shown in the software by using a unique identifier.
 * */
public final class LanguageManager extends Observable {
    /**
     * Initializes the language manager. This reads in all provided
     * language resource files and creates a list of available languages.
     * It also sets the locale to the one saved at the last execution
     * of the software or falls back to the default if this fails.
     */
    private LanguageManager() {
    }

    /**
     * Sets the locale to the provided one and notifies all
     * observers to update their text.
     * 
     * @param locale    New locale to be used
     */
    public void setLocale(Locale locale) {
        // TODO
    }

    /**
     * Getter-Method for the unique string respective to the current language. This uses
     * a unique identifier that resolves to the respective translation in all
     * supported languages.
     * 
     * @param key   Unique identifier to find the translated string for the current language
     * @return      Translated string according to the respective unique identifier
     */
    public String getString(String key) {
        // TODO
        return "";
    }

    /**
     * Getter-Method for the single instance that exists of this class. If none exists,
     * create a new one.
     * 
     * @return Instance of this class
     */
    public static LanguageManager getInstance() {
        // TODO
        return null;
    }

    /**
     * Getter-Method for an immutable list of all supported languages to choose from.
     * 
     * @return Immutable list of all supported languages
     */
    public List<Locale> getSupportedLanguages() {
        // TODO
        return null;
    }
}
