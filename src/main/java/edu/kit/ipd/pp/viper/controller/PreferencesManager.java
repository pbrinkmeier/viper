package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;

/**
 * The preference manager handles settings like the selected program language by
 * saving them to the disk
 */
public class PreferencesManager {
    /**
     * Name of properties file that will be created in the users home directory
     */
    private static final String FILE_NAME = ".viper";

    /**
     * Keys used in properties file
     */
    private static final String KEY_LANGUAGE = "language";

    /**
     * Default values in case the properties file does not exist
     */
    private static final String DEFAULT_LANGUAGE = "de";

    private final ConsolePanel console;

    private File propertiesFile;
    private Properties properties;

    /**
     * Creates a new preference manager
     * 
     * @param console Instance of console panel
     */
    public PreferencesManager(ConsolePanel console) {
        this.console = console;

        this.properties = new Properties();
        this.propertiesFile = new File(PreferencesManager.getUserDirectory() + PreferencesManager.FILE_NAME);

        try {
            FileReader reader = new FileReader(this.propertiesFile);
            this.properties.load(reader);
            reader.close();
        } catch (IOException e) {
            this.console.printLine("Properties file '" + this.propertiesFile.getAbsolutePath() + "' doesn't exist yet",
                    LogType.DEBUG);
        }
    }

    private void writeProperty(String key, String value) {
        FileWriter writer;
        try {
            writer = new FileWriter(this.propertiesFile);

            // write property and close handle
            this.properties.setProperty(key, value);

            this.properties.store(writer, null);
            writer.close();
        } catch (IOException e) {
            this.console.printLine("Could not write properties file to '" + this.propertiesFile.getAbsolutePath() + "'",
                    LogType.DEBUG);
        }
    }

    private static String getUserDirectory() {
        return System.getProperty("user.home") + File.separator;
    }

    /**
     * Returns the selected language
     * 
     * @return Selected language, the default is set to german
     */
    public Locale getLanguage() {
        String language;
        if (this.properties == null) {
            language = DEFAULT_LANGUAGE;
        } else {
            language = this.properties.getProperty(KEY_LANGUAGE, DEFAULT_LANGUAGE);
        }

        return this.getLocaleByLanguage(language);
    }

    /**
     * Sets the selected language
     * 
     * @param locale Set locale
     */
    public void setLanguage(Locale locale) {
        String language = locale.getLanguage();
        this.writeProperty("language", language);
    }

    private Locale getLocaleByLanguage(String language) {
        List<Locale> supportedLocales = LanguageManager.getInstance().getSupportedLocales();
        Iterator<Locale> iter = supportedLocales.iterator();
        while (iter.hasNext()) {
            Locale locale = iter.next();
            if (locale.getLanguage().equals(language))
                return locale;
        }

        return null;
    }
}
