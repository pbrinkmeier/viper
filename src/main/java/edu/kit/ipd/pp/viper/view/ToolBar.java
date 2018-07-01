package edu.kit.ipd.pp.viper.view;

import javax.swing.JToolBar;

import edu.kit.ipd.pp.viper.controller.CommandContinue;
import edu.kit.ipd.pp.viper.controller.CommandFormat;
import edu.kit.ipd.pp.viper.controller.CommandNew;
import edu.kit.ipd.pp.viper.controller.CommandNextStep;
import edu.kit.ipd.pp.viper.controller.CommandOpen;
import edu.kit.ipd.pp.viper.controller.CommandParse;
import edu.kit.ipd.pp.viper.controller.CommandSave;
import edu.kit.ipd.pp.viper.controller.SaveType;

public class ToolBar extends JToolBar {
    private static final String ICON_NEW = "/icon_placeholder.png";
    private static final String ICON_OPEN = "/icon_placeholder.png";
    private static final String ICON_SAVE = "/icon_placeholder.png";
    private static final String ICON_PARSE = "/icon_placeholder.png";
    private static final String ICON_FORMAT = "/icon_placeholder.png";
    private static final String ICON_STEP = "/icon_placeholder.png";
    private static final String ICON_SOLUTION = "/icon_placeholder.png";

    private MainWindow main;

    /**
     * @param gui
     */
    public ToolBar(MainWindow gui) {
        this.main = gui;

        this.setFloatable(false);
        this.setRollover(true);

        this.addButtons();
    }

    private void addButtons() {
        this.add(new Button(ICON_NEW, "tooltip_new", new CommandNew(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel())));

        this.add(new Button(ICON_OPEN, "tooltip_open", new CommandOpen(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel())));

        this.add(new Button(ICON_SAVE, "tooltip_save",
                new CommandSave(this.main.getConsolePanel(), this.main.getEditorPanel(), SaveType.SAVE)));

        this.addSeparator();

        this.add(new Button(ICON_PARSE, "tooltip_parse", new CommandParse(this.main.getConsolePanel(),
                this.main.getEditorPanel(), this.main.getVisualisationPanel(), this.main.getInterpreterManager())));

        this.add(new Button(ICON_FORMAT, "tooltip_format",
                new CommandFormat(this.main.getConsolePanel(), this.main.getEditorPanel())));

        this.addSeparator();

        this.add(new Button(ICON_STEP, "tooltip_step", new CommandNextStep(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), this.main.getInterpreterManager())));

        this.add(new Button(ICON_SOLUTION, "tooltip_next", new CommandContinue(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), this.main.getInterpreterManager())));
    }
}
