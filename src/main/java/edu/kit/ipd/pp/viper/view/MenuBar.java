package edu.kit.ipd.pp.viper.view;

import java.util.Iterator;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import edu.kit.ipd.pp.viper.controller.CommandExit;
import edu.kit.ipd.pp.viper.controller.CommandExportImage;
import edu.kit.ipd.pp.viper.controller.CommandExportTikz;
import edu.kit.ipd.pp.viper.controller.CommandFormat;
import edu.kit.ipd.pp.viper.controller.CommandNew;
import edu.kit.ipd.pp.viper.controller.CommandOpen;
import edu.kit.ipd.pp.viper.controller.CommandParse;
import edu.kit.ipd.pp.viper.controller.CommandSave;
import edu.kit.ipd.pp.viper.controller.CommandSetLang;
import edu.kit.ipd.pp.viper.controller.CommandToggleLib;
import edu.kit.ipd.pp.viper.controller.ImageFormat;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;
import edu.kit.ipd.pp.viper.controller.SaveType;

public class MenuBar extends JMenuBar {
    /**
     * Instance of main window
     */
    private final MainWindow main;

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
    }

    private void addFileMenu() {
        Menu menu = new Menu(LanguageKey.MENU_FILE);

        this.addNewItem(menu);
        this.addOpenItem(menu);
        this.addSaveItem(menu);
        this.addSaveAsItem(menu);
        menu.addSeparator();
        this.addRecentlyOpenedMenu(menu);
        menu.addSeparator();
        this.addExitItem(menu);

        this.add(menu);
    }

    private void addNewItem(Menu menu) {
        MenuItem item = new MenuItem(LanguageKey.MENU_NEW, new CommandNew(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel()));

        menu.add(item);
    }

    private void addOpenItem(Menu menu) {
        MenuItem item = new MenuItem(LanguageKey.MENU_OPEN, new CommandOpen(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel()));

        menu.add(item);
    }

    private void addSaveItem(Menu menu) {
        MenuItem item = new MenuItem(LanguageKey.MENU_SAVE,
                new CommandSave(this.main.getConsolePanel(), this.main.getEditorPanel(), SaveType.SAVE));

        menu.add(item);
    }

    private void addSaveAsItem(Menu menu) {
        MenuItem item = new MenuItem(LanguageKey.MENU_SAVEAS,
                new CommandSave(this.main.getConsolePanel(), this.main.getEditorPanel(), SaveType.SAVE_AS));

        menu.add(item);
    }

    private void addRecentlyOpenedMenu(Menu menu) {
        Menu usedMenu = new Menu(LanguageKey.MENU_RECENT);

        // TODO: loop all recently used files, for each: create MenuItem with
        // CommandOpen

        menu.add(usedMenu);
    }

    private void addExitItem(Menu menu) {
        MenuItem item = new MenuItem(LanguageKey.MENU_EXIT, new CommandExit());
        menu.add(item);
    }

    private void addProgramMenu() {
        Menu menu = new Menu(LanguageKey.MENU_PROGRAM);

        this.addParseItem(menu);
        this.addFormatItem(menu);

        this.add(menu);
    }

    private void addParseItem(Menu menu) {
        MenuItem item = new MenuItem(LanguageKey.MENU_PARSE, new CommandParse(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel(), this.main.getInterpreterManager()));

        menu.add(item);
    }

    private void addFormatItem(Menu menu) {
        MenuItem item = new MenuItem(LanguageKey.MENU_FORMAT, new CommandFormat(this.main.getConsolePanel(),
                this.main.getEditorPanel()));

        menu.add(item);
    }

    private void addExportMenu() {
        Menu menu = new Menu(LanguageKey.MENU_EXPORT);

        MenuItem itemPng = new MenuItem(LanguageKey.MENU_EXPORT_PNG, new CommandExportImage(this.main.getConsolePanel(),
                ImageFormat.PNG, this.main.getInterpreterManager()));

        MenuItem itemSvg = new MenuItem(LanguageKey.MENU_EXPORT_SVG, new CommandExportImage(this.main.getConsolePanel(),
                ImageFormat.SVG, this.main.getInterpreterManager()));

        MenuItem itemTikz = new MenuItem(LanguageKey.MENU_EXPORT_TIKZ, new CommandExportTikz(
                this.main.getConsolePanel(), this.main.getInterpreterManager()));

        menu.add(itemPng);
        menu.add(itemSvg);
        menu.add(itemTikz);

        this.add(menu);
    }

    private void addSettingsMenu() {
        Menu menu = new Menu(LanguageKey.MENU_SETTINGS);

        CheckBoxMenuItem item = new CheckBoxMenuItem(LanguageKey.MENU_STDLIB, new CommandToggleLib(
                this.main.getConsolePanel(), this.main.getVisualisationPanel(), this.main.getInterpreterManager()));
        item.setSelected(true);

        menu.add(item);
        this.addLanguageSwitchMenu(menu);

        this.add(menu);
    }

    private void addLanguageSwitchMenu(Menu menu) {
        Menu languageMenu = new Menu(LanguageKey.MENU_LANGUAGE);

        ButtonGroup languageGroup = new ButtonGroup();

        // TODO: using a boolean is ugly
        boolean select = true;
        Iterator<Locale> iter = LanguageManager.getInstance().getSupportedLocales().iterator();
        while (iter.hasNext()) {
            Locale locale = iter.next();

            LanguageCheckBoxMenuItem item = new LanguageCheckBoxMenuItem(locale, new CommandSetLang(
                    this.main.getConsolePanel(), this.main.getVisualisationPanel(), locale,
                    this.main.getInterpreterManager()));
            languageGroup.add(item);

            if (select) {
                item.setSelected(true);
                select = !select;
            }

            languageMenu.add(item);
        }

        menu.add(languageMenu);
    }
}
