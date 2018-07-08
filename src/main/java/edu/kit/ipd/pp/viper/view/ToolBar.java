package edu.kit.ipd.pp.viper.view;

import javax.swing.JToolBar;

import edu.kit.ipd.pp.viper.controller.CommandContinue;
import edu.kit.ipd.pp.viper.controller.CommandFormat;
import edu.kit.ipd.pp.viper.controller.CommandNew;
import edu.kit.ipd.pp.viper.controller.CommandNextStep;
import edu.kit.ipd.pp.viper.controller.CommandOpen;
import edu.kit.ipd.pp.viper.controller.CommandParse;
import edu.kit.ipd.pp.viper.controller.CommandSave;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.SaveType;

public class ToolBar extends JToolBar {
    /**
     * Path to icons used by {@link Button}s of this toolbar.
     * All paths need to begin with a slash, otherwise Java will look for the file inside the
     * <code>edu.kit.ipd.pp.viper.view</code> package, not inside <code>src/main/resources/</code>.
     */
    private static final String ICON_NEW = "/icons_png/icon_newfile.png";
    private static final String ICON_OPEN = "/icons_png/icon_openfile.png";
    private static final String ICON_SAVE = "/icons_png/icon_savefile.png";
    private static final String ICON_PARSE = "/icons_png/icon_parsefile.png";
    private static final String ICON_FORMAT = "/icons_png/icon_formatfile.png";
    private static final String ICON_STEP = "/icons_png/icon_singlestep.png";
    private static final String ICON_SOLUTION = "/icons_png/icon_continue.png";

    /**
     * Reference to main class
     */
    private final MainWindow main;

    /**
     * Creates a new toolbar that can be added to the main window
     * 
     * @param gui Reference to main window. Necessary because the commands executed by clicking on buttons in this
     *            toolbar need the different panels available via getters in the main class.
     */
    public ToolBar(MainWindow gui) {
        this.main = gui;

        this.setFloatable(false);
        this.setRollover(true);

        this.addButtons();
    }

    /**
     * Adds all buttons to the toolbar
     */
    private void addButtons() {
        this.add(new Button(ICON_NEW, LanguageKey.TOOLTIP_NEW, new CommandNew(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel())));

        this.add(new Button(ICON_OPEN, LanguageKey.TOOLTIP_OPEN, new CommandOpen(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel())));

        this.add(new Button(ICON_SAVE, LanguageKey.TOOLTIP_SAVE,
                new CommandSave(this.main.getConsolePanel(), this.main.getEditorPanel(), SaveType.SAVE)));

        this.addSeparator();

        this.add(new Button(ICON_PARSE, LanguageKey.TOOLTIP_PARSE, new CommandParse(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel())));

        this.add(new Button(ICON_FORMAT, LanguageKey.TOOLTIP_FORMAT,
                new CommandFormat(this.main.getConsolePanel(), this.main.getEditorPanel())));

        this.addSeparator();

        this.add(new Button(ICON_STEP, LanguageKey.TOOLTIP_STEP, new CommandNextStep(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), this.main.getInterpreterManager())));

        this.add(new Button(ICON_SOLUTION, LanguageKey.TOOLTIP_NEXT, new CommandContinue(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), this.main.getInterpreterManager())));
    }
}
