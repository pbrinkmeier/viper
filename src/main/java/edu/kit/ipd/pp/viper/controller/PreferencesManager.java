package edu.kit.ipd.pp.viper.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    private static final String KEY_STDLIB = "stdlib";

    /**
     * Default values in case the properties file does not exist
     */
    private static final String DEFAULT_LANGUAGE = "de";
    private static final boolean DEFAULT_STDLIB = true;

    private static final int MAX_RECENTLY_USED = 5;

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

        try (FileReader reader = new FileReader(this.propertiesFile);) {
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
            language = PreferencesManager.DEFAULT_LANGUAGE;
        } else {
            language = this.properties.getProperty(PreferencesManager.KEY_LANGUAGE,
                    PreferencesManager.DEFAULT_LANGUAGE);
        }

        return PreferencesManager.getLocaleByLanguage(language);
    }

    /**
     * Checks whether the standard lib is enabled or not
     * 
     * @return true if enabled, false otherwise
     */
    public boolean isStandardLibEnabled() {
        boolean enabled;
        if (this.properties == null) {
            enabled = true;
        } else {
            String status = this.properties.getProperty(PreferencesManager.KEY_STDLIB,
                    String.valueOf(PreferencesManager.DEFAULT_STDLIB));
            enabled = Boolean.parseBoolean(status);
        }

        return enabled;
    }

    /**
     * Sets the selected language
     * 
     * @param locale Set locale
     */
    public void setLanguage(Locale locale) {
        String language = locale.getLanguage();
        this.writeProperty(PreferencesManager.KEY_LANGUAGE, language);
    }

    private static Locale getLocaleByLanguage(String language) {
        List<Locale> supportedLocales = LanguageManager.getInstance().getSupportedLocales();
        Iterator<Locale> iter = supportedLocales.iterator();
        while (iter.hasNext()) {
            Locale locale = iter.next();
            if (locale.getLanguage().equals(language))
                return locale;
        }

        return null;
    }

    /**
     * Sets whether the standard lib is enabled or not
     * 
     * @param enabled true is enabled, false otherwise
     */
    public void setStandardLibEnabled(boolean enabled) {
        String status = String.valueOf(enabled);
        this.writeProperty(PreferencesManager.KEY_STDLIB, status);
    }

    /**
     * Sets the list of file references in the properties file.
     * 
     * @param references the references to be set
     */
    public void setFileReferences(List<File> references) {
        for (int i = 0; i < references.size(); i++)
            this.writeProperty("recentlyUsedFile" + i, references.get(i).getAbsolutePath());
    }

    /**
     * Returns the list of file references in the properties file.
     * 
     * @return the list of file references in the properties file
     */
    public ArrayList<File> getFileReferences() {
        ArrayList<File> list = new ArrayList<File>();

        for (int i = 0; i < MAX_RECENTLY_USED; i++) {
            final String path = this.properties.getProperty("recentlyUsedFile" + i);
            if (path != null) {
                File f = new File(path);
                if (f.exists() && f.isFile() && !list.contains(f))
                    list.add(f);
            }
        }

        return list;
    }
}
