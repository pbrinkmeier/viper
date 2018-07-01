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
import edu.kit.ipd.pp.viper.controller.LanguageManager;
import edu.kit.ipd.pp.viper.controller.SaveType;

public class MenuBar extends JMenuBar {
    /**
     * Instance of main window
     */
    private MainWindow main;

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
        Menu menu = new Menu("menu_file");

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
        MenuItem item = new MenuItem("menu_new", new CommandNew(this.main.getConsolePanel(), this.main.getEditorPanel(),
                this.main.getVisualisationPanel()));

        menu.add(item);
    }

    private void addOpenItem(Menu menu) {
        MenuItem item = new MenuItem("menu_open", new CommandOpen(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel()));

        menu.add(item);
    }

    private void addSaveItem(Menu menu) {
        MenuItem item = new MenuItem("menu_save",
                new CommandSave(this.main.getConsolePanel(), this.main.getEditorPanel(), SaveType.SAVE));

        menu.add(item);
    }

    private void addSaveAsItem(Menu menu) {
        MenuItem item = new MenuItem("menu_saveas",
                new CommandSave(this.main.getConsolePanel(), this.main.getEditorPanel(), SaveType.SAVE_AS));

        menu.add(item);
    }

    private void addRecentlyOpenedMenu(Menu menu) {
        Menu usedMenu = new Menu("menu_recent");

        // TODO: loop all recently used files, for each: create MenuItem with
        // CommandOpen

        menu.add(usedMenu);
    }

    private void addExitItem(Menu menu) {
        MenuItem item = new MenuItem("menu_exit", new CommandExit());
        menu.add(item);
    }

    private void addProgramMenu() {
        Menu menu = new Menu("menu_program");

        this.addParseItem(menu);
        this.addFormatItem(menu);

        this.add(menu);
    }

    private void addParseItem(Menu menu) {
        MenuItem item = new MenuItem("menu_parse", new CommandParse(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel(), this.main.getInterpreterManager()));

        menu.add(item);
    }

    private void addFormatItem(Menu menu) {
        MenuItem item = new MenuItem("menu_format",
                new CommandFormat(this.main.getConsolePanel(), this.main.getEditorPanel()));

        menu.add(item);
    }

    private void addExportMenu() {
        Menu menu = new Menu("menu_export");

        MenuItem itemPng = new MenuItem("menu_export_png", new CommandExportImage(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), ImageFormat.PNG));

        MenuItem itemSvg = new MenuItem("menu_export_svg", new CommandExportImage(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), ImageFormat.SVG));

        MenuItem itemTikz = new MenuItem("menu_export_tikz", new CommandExportTikz(this.main.getConsolePanel(),
                this.main.getVisualisationPanel()));

        menu.add(itemPng);
        menu.add(itemSvg);
        menu.add(itemTikz);

        this.add(menu);
    }

    private void addSettingsMenu() {
        Menu menu = new Menu("menu_settings");

        CheckBoxMenuItem item = new CheckBoxMenuItem("menu_stdlib", new CommandToggleLib(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), this.main.getInterpreterManager()));
        item.setSelected(true);

        menu.add(item);
        this.addLanguageSwitchMenu(menu);

        this.add(menu);
    }

    private void addLanguageSwitchMenu(Menu menu) {
        Menu languageMenu = new Menu("menu_language");

        ButtonGroup languageGroup = new ButtonGroup();

        boolean select = true;
        Iterator<Locale> iter = LanguageManager.getInstance().getSupportedLocales().iterator();
        while (iter.hasNext()) {
            Locale locale = iter.next();

            CheckBoxMenuItem item = new CheckBoxMenuItem("menu_language_" + locale.getLanguage(),
                    new CommandSetLang(this.main.getConsolePanel(), this.main.getVisualisationPanel(), locale,
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
