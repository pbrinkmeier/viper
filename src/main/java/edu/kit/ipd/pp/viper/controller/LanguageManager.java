package edu.kit.ipd.pp.viper.controller;

import java.util.List;
import java.util.Locale;
import java.util.Observable;

public class LanguageManager extends Observable {
    /**
     * Default constructor
     */
    private LanguageManager() {
    }

    /**
     * @param locale 
     * @return
     */
    public void setLocale(Locale locale) {
        // TODO
    }

    /**
     * @param key 
     * @return
     */
    public String getString(String key) {
        // TODO
        return "";
    }

    /**
     * @return
     */
    public static LanguageManager getInstance() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public List<Locale> getSupportedLanguages() {
        // TODO
        return null;
    }
}
