package edu.kit.ipd.pp.viper.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.kit.ipd.pp.viper.controller.CommandExportImage;
import edu.kit.ipd.pp.viper.controller.CommandOpen;
import edu.kit.ipd.pp.viper.controller.CommandResetZoom;
import edu.kit.ipd.pp.viper.controller.CommandSave;
import edu.kit.ipd.pp.viper.controller.CommandSetLang;
import edu.kit.ipd.pp.viper.controller.CommandToggleLib;
import edu.kit.ipd.pp.viper.controller.ImageFormat;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;
import edu.kit.ipd.pp.viper.controller.SaveType;

/**
 * Represents a menu bar containing all available options and functionalities of
 * the program.
 */
public class MenuBar extends JMenuBar implements HasClickable {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -8638583278667065231L;

    private static final String SAMPLES_FOLDER = "samples";

    /**
     * Instance of main window
     */
    private final MainWindow main;

    private MenuItem itemNew;
    private MenuItem itemOpen;
    private MenuItem itemSave;
    private MenuItem itemSaveAs;
    private MenuItem itemExit;
    private MenuItem itemParse;
    private MenuItem itemFormat;
    private MenuItem itemExportPNG;
    private MenuItem itemExportSVG;
    private MenuItem itemAbout;
    private MenuItem itemShowStandard;
    private MenuItem itemManual;
    private MenuItem itemResetZoom;
    private CheckBoxMenuItem itemToggleSTD;

    private Menu recentlyUsedMenu;
    private Menu sampleProgramsMenu;

    /**
     * The constructor initialises all menus in the menu bar
     * 
     * @param gui Instance of main window
     */
    public MenuBar(MainWindow gui) {
        this.main = gui;

        this.addFileMenu();
        this.addProgramMenu();
        this.addExportMenu();
        this.addSettingsMenu();
        this.addHelpMenu();
    }

    /**
     * Adds the "file" menu
     */
    private void addFileMenu() {
        Menu menu = new Menu(LanguageKey.MENU_FILE);

        this.addNewItem(menu);
        this.addOpenItem(menu);
        this.addSaveItem(menu);
        this.addSaveAsItem(menu);
        menu.addSeparator();
        this.addRecentlyOpenedMenu(menu);
        this.addSampleProgramsMenu(menu);
        menu.addSeparator();
        this.addExitItem(menu);

        this.resetRecentlyOpenedMenu();
        this.loadSamplePrograms();
        this.add(menu);
    }

    /**
     * Adds the "file > new" item
     * 
     * @param menu Menu to attach to
     */
    private void addNewItem(Menu menu) {
        this.itemNew = new MenuItem(LanguageKey.MENU_NEW, this.main.getCommandNew(), KeyboardShortcut.NEW);
        menu.add(this.itemNew);
    }

    /**
     * Adds the "file > open" item
     * 
     * @param menu Menu to attach to
     */
    private void addOpenItem(Menu menu) {
        this.itemOpen = new MenuItem(LanguageKey.MENU_OPEN, this.main.getCommandOpen(), KeyboardShortcut.OPEN);
        menu.add(this.itemOpen);
    }

    /**
     * Adds the "file > save" item
     * 
     * @param menu Menu to attach to
     */
    private void addSaveItem(Menu menu) {
        this.itemSave = new MenuItem(LanguageKey.MENU_SAVE, this.main.getCommandSave(), KeyboardShortcut.SAVE);
        menu.add(this.itemSave);
    }

    /**
     * Adds the "file > save as" item
     * 
     * @param menu Menu to attach to
     */
    private void addSaveAsItem(Menu menu) {
        this.itemSaveAs = new MenuItem(LanguageKey.MENU_SAVEAS,
                new CommandSave(this.main.getConsolePanel(), this.main.getEditorPanel(), SaveType.SAVE_AS,
                        this.main::setWindowTitle, this.main.getInterpreterManager()),
                KeyboardShortcut.SAVE_AS);
        menu.add(this.itemSaveAs);
    }

    /**
     * Adds the "file > recently opened" item
     * 
     * @param menu Menu to attach to
     */
    private void addRecentlyOpenedMenu(Menu menu) {
        this.recentlyUsedMenu = new Menu(LanguageKey.MENU_RECENT);
        menu.add(this.recentlyUsedMenu);
    }

    /**
     * Clears the recently used menu and adds the current items to it
     */
    public void resetRecentlyOpenedMenu() {
        this.recentlyUsedMenu.removeAll();
        for (File f : this.main.getEditorPanel().getFileReferenceList()) {
            JMenuItem item = new JMenuItem(f.getAbsolutePath());
            CommandOpen command = new CommandOpen(f.getAbsolutePath(), false, this.main.getConsolePanel(),
                    this.main.getEditorPanel(), this.main.getVisualisationPanel(), this.main::setWindowTitle,
                    this.main::switchClickableState, this.main.getCommandSave(), this.main.getInterpreterManager());

            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    command.execute();
                }
            });
            this.recentlyUsedMenu.add(item);
        }
    }

    /**
     * Adds the "file > sample programs" item
     * 
     * @param menu Menu to attach to
     */
    private void addSampleProgramsMenu(Menu menu) {
        this.sampleProgramsMenu = new Menu(LanguageKey.MENU_SAMPLES);
        menu.add(this.sampleProgramsMenu);
    }

    /**
     * Loads all sample programs from src/main/resource/samples/ folder and adds them
     * as menu items
     * 
     * Unfortunately, when packed as jar file, getting the content of a directory is not really
     * straightforward. The entire jar file need to be indexed and searched for the actual folder
     * we're looking for.
     */
    private void loadSamplePrograms() {
        File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        if (jarFile.isFile()) {
            try {
                JarFile jar = new JarFile(jarFile);
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();

                    if (name.startsWith(MenuBar.SAMPLES_FOLDER + "/") && name.endsWith(".pl")) {
                        this.addSampleProgram(name.substring(name.lastIndexOf(File.separator) + 1), name, true);
                    }
                }

                jar.close();
            } catch (IOException e) {
                this.main.getConsolePanel().printLine("Could not load sample programs", LogType.DEBUG);
            }
        } else {
            File[] samplesFolder = null;

            try {
                samplesFolder = new File(this.main.getClass().getResource("/" + MenuBar.SAMPLES_FOLDER).toURI())
                        .listFiles();
            } catch (URISyntaxException e) {
                this.main.getConsolePanel().printLine("Could not load sample programs", LogType.DEBUG);
            }

            for (File file : samplesFolder) {
                if (file.isDirectory() || !file.getName().endsWith(".pl")) {
                    continue;
                }

                this.addSampleProgram(file.getName(), file.getAbsolutePath(), false);
            }
        }
    }

    /**
     * Adds a sample program menu item
     * 
     * @param name Name to display (file name)
     * @param path Path to file
     */
    private void addSampleProgram(String name, String path, boolean isResource) {
        JMenuItem item = new JMenuItem(name);
        CommandOpen command = new CommandOpen(path, isResource, this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel(), this.main::setTitle,
                this.main::switchClickableState, this.main.getCommandSave(),
                this.main.getInterpreterManager());

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command.execute();
            }
        });

        this.sampleProgramsMenu.add(item);
    }

    /**
     * Adds the "file > exit" item
     * 
     * @param menu Menu to attach to
     */
    private void addExitItem(Menu menu) {
        this.itemExit = new MenuItem(LanguageKey.MENU_EXIT, this.main.getCommandExit(), KeyboardShortcut.EXIT);
        menu.add(this.itemExit);
    }

    /**
     * Adds the "program" menu
     */
    private void addProgramMenu() {
        Menu menu = new Menu(LanguageKey.MENU_PROGRAM);

        this.addParseItem(menu);
        this.addFormatItem(menu);

        this.add(menu);
    }

    /**
     * Adds the "program > parse" item
     * 
     * @param menu Menu to attach to
     */
    private void addParseItem(Menu menu) {
        this.itemParse = new MenuItem(LanguageKey.MENU_PARSE, this.main.getCommandParse(), KeyboardShortcut.PARSE);
        menu.add(this.itemParse);
    }

    /**
     * Adds the "program > format" item
     * 
     * @param menu Menu to attach to
     */
    private void addFormatItem(Menu menu) {
        this.itemFormat = new MenuItem(LanguageKey.MENU_FORMAT, this.main.getCommandFormat(), KeyboardShortcut.FORMAT);
        menu.add(this.itemFormat);
    }

    /**
     * Adds the "export" menu
     */
    private void addExportMenu() {
        Menu menu = new Menu(LanguageKey.MENU_EXPORT);

        this.itemExportPNG = new MenuItem(LanguageKey.MENU_EXPORT_PNG, new CommandExportImage(
                this.main.getConsolePanel(), ImageFormat.PNG, this.main.getInterpreterManager()), null);

        this.itemExportSVG = new MenuItem(LanguageKey.MENU_EXPORT_SVG, new CommandExportImage(
                this.main.getConsolePanel(), ImageFormat.SVG, this.main.getInterpreterManager()), null);

        menu.add(this.itemExportPNG);
        menu.add(this.itemExportSVG);

        this.add(menu);
    }

    /**
     * Adds the "settings" menu
     */
    private void addSettingsMenu() {
        Menu menu = new Menu(LanguageKey.MENU_SETTINGS);

        this.itemToggleSTD = new CheckBoxMenuItem(LanguageKey.MENU_STDLIB,
                new CommandToggleLib(this.main.getConsolePanel(), this.main.getVisualisationPanel(),
                        this.main.getInterpreterManager(), this.main.getPreferencesManager(),
                        this.main::switchClickableState));
        if (this.main.getPreferencesManager().isStandardLibEnabled()) {
            this.itemToggleSTD.setSelected(true);
        } else {
            // standard lib is enabled by default, turn off here
            this.main.getInterpreterManager().toggleStandardLibrary();
        }

        menu.add(this.itemToggleSTD);

        this.itemResetZoom = new MenuItem(LanguageKey.MENU_RESET_ZOOM,
                new CommandResetZoom(this.main.getVisualisationPanel(),
                                     this.main.getConsolePanel(),
                                     this.main.getEditorPanel()),
                KeyboardShortcut.RESET_ZOOM);
        menu.add(this.itemResetZoom);

        this.addLanguageSwitchMenu(menu);

        this.add(menu);
    }

    /**
     * Adds the "settings > language" item
     * 
     * @param menu Menu to attach to
     */
    private void addLanguageSwitchMenu(Menu menu) {
        Menu languageMenu = new Menu(LanguageKey.MENU_LANGUAGE);

        ButtonGroup languageGroup = new ButtonGroup();

        // get the currently set locale from the preferences manager
        Locale configLocale = this.main.getPreferencesManager().getLanguage();

        Iterator<Locale> iter = LanguageManager.getInstance().getSupportedLocales().iterator();
        while (iter.hasNext()) {
            Locale locale = iter.next();

            LanguageCheckBoxMenuItem item = new LanguageCheckBoxMenuItem(locale,
                    new CommandSetLang(this.main.getConsolePanel(), this.main.getVisualisationPanel(), locale,
                            this.main.getInterpreterManager(), this.main.getPreferencesManager()));
            languageGroup.add(item);

            if (locale.getLanguage().equals(configLocale.getLanguage()))
                item.setSelected(true);

            languageMenu.add(item);
        }

        menu.add(languageMenu);
    }
    
    /**
     * Adds the "help" menu
     */
    private void addHelpMenu() {
        Menu helpMenu = new Menu(LanguageKey.MENU_HELP);

        this.itemShowStandard = new MenuItem(LanguageKey.MENU_SHOW_STANDARD, this.main.getCommandShowStandard(), null);
        helpMenu.add(this.itemShowStandard);

        this.itemManual = new MenuItem(LanguageKey.MANUAL, this.main.getCommandShowManual(), null);
        helpMenu.add(this.itemManual);

        this.itemAbout = new MenuItem(LanguageKey.MENU_ABOUT, this.main.getCommandShowAbout(), null);
        helpMenu.add(this.itemAbout);

        this.add(helpMenu);
    }

    @Override
    public void switchClickableState(ClickableState state) {
        switch (state) {
        case NOT_PARSED_YET:
        case PARSED_PROGRAM:
            this.itemNew.setEnabled(true);
            this.itemOpen.setEnabled(true);
            this.itemSave.setEnabled(true);
            this.itemSaveAs.setEnabled(true);
            this.itemExit.setEnabled(true);
            this.itemParse.setEnabled(true);
            this.itemFormat.setEnabled(true);
            this.itemExportPNG.setEnabled(false);
            this.itemExportSVG.setEnabled(false);
            this.itemToggleSTD.setEnabled(true);
            this.itemAbout.setEnabled(true);
            this.itemShowStandard.setEnabled(true);
            break;
        case PARSED_QUERY:
        case FIRST_STEP:
        case LAST_STEP:
        case NO_MORE_SOLUTIONS:
            this.itemNew.setEnabled(true);
            this.itemOpen.setEnabled(true);
            this.itemSave.setEnabled(true);
            this.itemSaveAs.setEnabled(true);
            this.itemExit.setEnabled(true);
            this.itemParse.setEnabled(true);
            this.itemFormat.setEnabled(true);
            this.itemExportPNG.setEnabled(true);
            this.itemExportSVG.setEnabled(true);
            this.itemToggleSTD.setEnabled(true);
            this.itemAbout.setEnabled(true);
            this.itemShowStandard.setEnabled(true);
            break;
        default:
            break;
        }
    }
}
