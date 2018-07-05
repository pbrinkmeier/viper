package edu.kit.ipd.pp.viper.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Central language manager singleton class. Reads in a list of translations for
 * the supported languages and provides a global interface to set the language
 * and update all relevant GUI components using observation. Internally, a
 * resource bundle is used to provide translated strings for any text shown in
 * the software by using a unique identifier.
 */
public final class LanguageManager extends Observable {
    private static LanguageManager instance;
    private static Locale currentLocale;

    /**
     * Bundle for current locale
     */
    private ResourceBundle bundle;

    /**
     * List of supported locales
     * 
     * To add a new language, create a new {@link Locale} entry in this list and add
     * a translations_{code}.properties file in the src folder
     */
    private final Locale[] supportedLocales = {new Locale("de"), new Locale("en")};

    /**
     * Initializes the language manager. This reads in all provided language
     * resource files and creates a list of available languages. It also sets the
     * locale to the one saved at the last execution of the software or falls back
     * to the default if this fails.
     */
    private LanguageManager() {
        Locale.setDefault(this.supportedLocales[0]);
        this.setLocale(Locale.getDefault());
    }

    /**
     * Getter-Method for the unique string respective to the current language. This
     * uses a unique identifier that resolves to the respective translation in all
     * supported languages.
     * 
     * @param key Unique identifier to find the translated string for the current
     * language
     * @return Translated string according to the respective unique identifier
     */
    public String getString(LanguageKey key) {
        try {
            return bundle.getString(key.getString());
        } catch (NullPointerException | MissingResourceException | ClassCastException e) {
            // show empty string instead of an error message
            return "";
        }
    }

    /**
     * Getter-Method for the single instance that exists of this class. If none
     * exists, create a new one.
     * 
     * @return Instance of this class
     */
    public static LanguageManager getInstance() {
        if (LanguageManager.instance == null)
            LanguageManager.instance = new LanguageManager();

        return LanguageManager.instance;
    }

    /**
     * Sets the locale to the provided one and notifies all observers to update
     * their text.
     * 
     * @param locale New locale to be used
     */
    public void setLocale(Locale locale) {
        if (!Arrays.asList(this.supportedLocales).contains(locale))
            return;

        LanguageManager.currentLocale = locale;
        bundle = ResourceBundle.getBundle("translations", locale);
        this.setChanged();

        this.notifyObservers();
    }

    /**
     * Getter-Method for an immutable list of all supported languages to choose
     * from.
     * 
     * @return Immutable list of all supported languages
     */
    public List<Locale> getSupportedLocales() {
        return Collections.unmodifiableList(Arrays.asList(supportedLocales));
    }

    /**
     * Returns the locale that is currently set
     * 
     * @return Current locale
     */
    public static Locale getCurrentLocale() {
        return LanguageManager.currentLocale;
    }
}
