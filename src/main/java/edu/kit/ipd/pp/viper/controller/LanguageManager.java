package edu.kit.ipd.pp.viper.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Observable;
import java.util.ResourceBundle;

public class LanguageManager extends Observable {
	/**
	 * Global instance of this LanguageManager (singleton pattern)
	 */
	private static LanguageManager instance;
	
	/**
	 * Bundle for current locale
	 */
	private ResourceBundle bundle;
	
	/**
	 * List of supported locales
	 * 
	 * To add a new language, create a new {@link Locale} entry in this
	 * list and add a translations_<code>.properties file in tthe src
	 * folder
	 */
	private final Locale[] supportedLocales = {
		    new Locale("de"),
		    new Locale("en")
	};
	
	/**
	 * Private constructor
	 * 
	 * Sets the default locale
	 */
	private LanguageManager() {
		Locale.setDefault(this.supportedLocales[0]);
		this.setLocale(Locale.getDefault());
	}
	
	/**
	 * Returns a translation for a given key
	 * 
	 * @param key The translation to fetch
	 * @return Translation for locale set by {@link LanguageManager#setLocale(Locale)} or the default language. Returns empty string if translation not found.
	 */
	public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch(NullPointerException | MissingResourceException | ClassCastException e) {
			// show empty string instead of an error message
			return "";
		}
	}
	
	/**
	 * Returns instance of this manager
	 * 
	 * @return {@link LanguageManager} instance
	 */
	public static LanguageManager getInstance() {
		if (LanguageManager.instance == null)
			LanguageManager.instance = new LanguageManager();
		
		return LanguageManager.instance;
	}
	
	/**
	 * Sets the locale to use for translations. If the given locale is not
	 * supported, calling setLocale will have no effect.
	 * 
	 * @param locale New locale
	 */
	public void setLocale(Locale locale) {
		if (!Arrays.asList(this.supportedLocales).contains(locale))
			return;
		
		bundle = ResourceBundle.getBundle("translations", locale);
	}
	
	/**
	 * Returns list of supported locales
	 * 
	 * @return List of supported languages
	 */
	public List<Locale> getSupportedLocales() {
		return Arrays.asList(supportedLocales);
	}
}
