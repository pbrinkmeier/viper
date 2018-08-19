package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import edu.kit.ipd.pp.viper.controller.CommandCancel;
import edu.kit.ipd.pp.viper.controller.CommandExit;
import edu.kit.ipd.pp.viper.controller.CommandFormat;
import edu.kit.ipd.pp.viper.controller.CommandNew;
import edu.kit.ipd.pp.viper.controller.CommandNextSolution;
import edu.kit.ipd.pp.viper.controller.CommandNextStep;
import edu.kit.ipd.pp.viper.controller.CommandOpen;
import edu.kit.ipd.pp.viper.controller.CommandParse;
import edu.kit.ipd.pp.viper.controller.CommandPreviousStep;
import edu.kit.ipd.pp.viper.controller.CommandSave;
import edu.kit.ipd.pp.viper.controller.CommandShowAbout;
import edu.kit.ipd.pp.viper.controller.CommandShowManual;
import edu.kit.ipd.pp.viper.controller.CommandShowStandard;
import edu.kit.ipd.pp.viper.controller.CommandZoom;
import edu.kit.ipd.pp.viper.controller.InterpreterManager;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;
import edu.kit.ipd.pp.viper.controller.PreferencesManager;
import edu.kit.ipd.pp.viper.controller.SaveType;
import edu.kit.ipd.pp.viper.controller.ZoomType;

/**
 * Represents the main window containing all the panel elements.
 */
public class MainWindow extends JFrame {
    /**
     * VIPER version number
     * 
     * The version number follows the MAJOR.MINOR scheme, change this on each new
     * release!
     */
    public static final String VERSION = "1.0";

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -5807530819617746945L;

    /**
     * Stores whether VIPER was started in debug mode or not. Debug mode will print
     * some additional information to the console.
     */
    private static boolean debug;

    /**
     * Window title
     */
    private static final String WINDOW_TITLE = "VIPER";

    /**
     * Window icon
     * 
     * The icon path has to begin with a slash, otherwise Java will look up the file
     * in the package folder, not in src/main/resources
     */
    private static final String WINDOW_ICON = "/icons_png/viper-icon.png";

    /**
     * Instances of all three panels
     */
    private final EditorPanel editorPanel;
    private final ConsolePanel consolePanel;
    private final VisualisationPanel visualisationPanel;

    private final CommandNew commandNew;
    private final CommandOpen commandOpen;
    private final CommandSave commandSave;
    private final CommandExit commandExit;
    private final CommandParse commandParse;
    private final CommandFormat commandFormat;
    private final CommandPreviousStep commandPreviousStep;
    private final CommandNextStep commandNextStep;
    private final CommandNextSolution commandNextSolution;
    private final CommandCancel commandCancel;
    private final CommandShowAbout commandShowAbout;
    private final CommandShowStandard commandShowStandard;
    private final CommandShowManual commandShowManual;
    
    private final CommandZoom commandZoomTextIn;
    private final CommandZoom commandZoomTextOut;
    
    private ToolBar toolbar;
    private MenuBar menubar;

    /**
     * Preferences manager instance
     */
    private final PreferencesManager prefManager;

    /**
     * Global instance of InterpreterManager
     */
    private InterpreterManager manager;
    
    /**
     * Global Window Title
     */
    private String windowTitle;

    /**
     * The constructor sets up the {@link JFrame} and initialises all three panels
     * 
     * @param debug Sets debug mode to enabled/disabled
     */
    public MainWindow(boolean debug) {
        MainWindow.debug = debug;

        this.setName(GUIComponentID.FRAME_MAIN.toString());
        this.setTitle(MainWindow.WINDOW_TITLE);
        this.setResizable(true);
        this.setIconImage(new ImageIcon(this.getClass().getResource(MainWindow.WINDOW_ICON)).getImage());

        // use system built-in look and feel instead of default swing look
        MainWindow.setDesign();

        this.manager = new InterpreterManager(this::switchClickableState);
        this.visualisationPanel = new VisualisationPanel(this);
        this.consolePanel = new ConsolePanel(this);
        this.prefManager = new PreferencesManager(this.consolePanel);
        this.consolePanel.setPreferencesManager(this.prefManager);
        this.editorPanel = new EditorPanel(this);

        // Create command instances
        this.commandSave = new CommandSave(this.consolePanel, this.editorPanel, SaveType.SAVE, this::setWindowTitle,
                this.manager);
        this.commandOpen = new CommandOpen(this.consolePanel, this.editorPanel, this.visualisationPanel,
                this::setWindowTitle, this::switchClickableState, this.commandSave, this.manager);
        this.commandNew = new CommandNew(this.consolePanel, this.editorPanel, this.visualisationPanel,
                this::setWindowTitle, this::switchClickableState, this.commandSave, this.manager);
        this.commandParse = new CommandParse(this.consolePanel, this.editorPanel, this.visualisationPanel,
                this.manager, this::switchClickableState);
        this.commandZoomTextIn = new CommandZoom(null, this.consolePanel, this.editorPanel, ZoomType.ZOOM_IN);
        this.commandZoomTextOut = new CommandZoom(null, this.consolePanel, this.editorPanel, ZoomType.ZOOM_OUT);
        this.commandFormat = new CommandFormat(this.consolePanel, this.editorPanel);
        this.commandPreviousStep = new CommandPreviousStep(this.visualisationPanel, this.manager);
        this.commandNextStep = new CommandNextStep(this.visualisationPanel, this.manager, this.consolePanel);
        this.commandNextSolution = new CommandNextSolution(this.consolePanel, this.visualisationPanel, this.manager);
        this.commandCancel = new CommandCancel(this.manager);
        this.commandExit = new CommandExit(this.editorPanel, this.commandSave, this.manager);
        this.commandShowAbout = new CommandShowAbout();
        this.commandShowStandard = new CommandShowStandard(this.manager);
        this.commandShowManual = new CommandShowManual();

        this.editorPanel.setZoomInCommand(this.commandZoomTextIn);
        this.editorPanel.setZoomOutCommand(this.commandZoomTextOut);
        
        // add menu bar and tool bar to window
        this.menubar = new MenuBar(this);
        this.toolbar = new ToolBar(this);

        this.setJMenuBar(this.menubar);
        this.getContentPane().add(this.toolbar, BorderLayout.NORTH);

        // set up layout using two split panes
        this.initLayout();

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // use CommandExit on JFrame close, because the editor may still contain
        // unsaved content
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainWindow.this.commandExit.execute();
            }
        });
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        this.switchClickableState(ClickableState.NOT_PARSED_YET);

        // load language from config and set it
        LanguageManager.getInstance().setLocale(this.prefManager.getLanguage());
        this.consolePanel.printLine(
                String.format(LanguageManager.getInstance().getString(LanguageKey.VIPER_READY), MainWindow.VERSION),
                LogType.INFO);
    }

    /**
     * Global clickable change function. This toggles all sub-elements in the GUI to
     * change their clickable state.
     * 
     * @param state The new state to be changed to
     */
    public void switchClickableState(ClickableState state) {
        this.menubar.switchClickableState(state);
        this.toolbar.switchClickableState(state);
        this.consolePanel.switchClickableState(state);
        this.visualisationPanel.switchClickableState(state);
    }

    /**
     * Sets the "look and feel" of the application by using the system default
     * theme.
     */
    private static void setDesign() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            if (MainWindow.inDebugMode())
                e.printStackTrace();
        }
    }

    /**
     * Sets up the main layout of the program. Two nested {@link JSplitPane}s are
     * used to realise the wanted layout.
     */
    private void initLayout() {
        // the inner pane splits up the right side into top (visualisation) and bottom
        // (console)
        JSplitPane innerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, this.visualisationPanel,
                this.consolePanel);
        innerPane.setResizeWeight(0.5);
        innerPane.setEnabled(true);
        innerPane.setDividerSize(5);

        // the outer pane splits up the window into a left (editor) and right (inner
        // pane) side
        JSplitPane outerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.editorPanel, innerPane);
        outerPane.setResizeWeight(0.5);
        outerPane.setEnabled(true);
        outerPane.setDividerSize(5);

        this.getContentPane().add(outerPane, BorderLayout.CENTER);
    }

    /**
     * Main method, creates a new instance of this class
     * 
     * @param args Command line arguments (ignored)
     */
    public static void main(String[] args) {
        boolean debug = false;
        for (String a : args) {
            if (a.equals("--debug"))
                debug = true;
        }

        new MainWindow(debug);
    }

    /**
     * Sets the {@link #windowTitle} and calls {@link #changeWindowTitle(String)}.
     * 
     * @param title String to set for {@link #windowTitle}
     */
    public void setWindowTitle(String title) {
        this.windowTitle = title;
        this.changeWindowTitle(this.windowTitle);
    }
    
    /**
     * Sets the window title to '{title} - VIPER'. This can be used to include the
     * currently opened file in the title.
     * 
     * @param title String to display, using "" or null removes the title
     */
    private void changeWindowTitle(String title) {
        if (title == null || title.equals(""))
            this.setTitle(MainWindow.WINDOW_TITLE);
        else
            this.setTitle(title + " - " + WINDOW_TITLE);
    }
    
    /** 
     * Appends an asterix to the window title
     * can be used when editor entry is changed
     * @param asterix boolean if * should be appended to title
     */
    public void setAppendAsterixToTitle(boolean asterix) {
        if (asterix) {
            if (!(this.windowTitle == null || this.windowTitle.equals("")))
                this.changeWindowTitle(this.windowTitle + "*"); 
        } else {
           this.changeWindowTitle(this.windowTitle);
        }
    }

    /**
     * Returns the debug mode
     * 
     * @return Debug mode enabled or not
     */
    public static boolean inDebugMode() {
        return MainWindow.debug;
    }

    /**
     * Returns the editor panel
     * 
     * @return Editor panel
     */
    public EditorPanel getEditorPanel() {
        return this.editorPanel;
    }

    /**
     * Returns the console panel
     * 
     * @return Console panel
     */
    public ConsolePanel getConsolePanel() {
        return this.consolePanel;
    }

    /**
     * Returns the visualisation panel
     * 
     * @return Visualisation panel
     */
    public VisualisationPanel getVisualisationPanel() {
        return this.visualisationPanel;
    }

    /**
     * Returns the interpreter manager
     * 
     * @return Interpreter manager
     */
    public InterpreterManager getInterpreterManager() {
        return this.manager;
    }

    /**
     * Returns the preferences manager
     * 
     * @return Preferences manager
     */
    public PreferencesManager getPreferencesManager() {
        return this.prefManager;
    }

    /**
     * Returns the menu bar (the one used internally, not the implicit AWT menu bar)
     * 
     * @return MenuBar
     */
    public MenuBar getInternalMenuBar() {
        return this.menubar;
    }

    /**
     * Returns the initialized exit command
     *
     * @return CommandExit
     */
    public CommandExit getCommandExit() {
        return this.commandExit;
    }

    /**
     * Returns the initialized new command
     *
     * @return CommandNew
     */
    public CommandNew getCommandNew() {
        return this.commandNew;
    }

    /**
     * Returns the initialized open command
     *
     * @return CommandOpen
     */
    public CommandOpen getCommandOpen() {
        return this.commandOpen;
    }

    /**
     * Returns the initialized save command
     *
     * @return CommandSave
     */
    public CommandSave getCommandSave() {
        return this.commandSave;
    }

    /**
     * Returns the initialized parse command
     *
     * @return CommandParse
     */
    public CommandParse getCommandParse() {
        return this.commandParse;
    }

    /**
     * Returns the initialized format command
     *
     * @return CommandFormat
     */
    public CommandFormat getCommandFormat() {
        return this.commandFormat;
    }

    /**
     * Returns the initialized previous step command
     *
     * @return CommandPreviousStep
     */
    public CommandPreviousStep getCommandPreviousStep() {
        return this.commandPreviousStep;
    }

    /**
     * Returns the initialized next step command
     *
     * @return CommandNextStep
     */
    public CommandNextStep getCommandNextStep() {
        return this.commandNextStep;
    }

    /**
     * Returns the initialized continue command
     *
     * @return CommandContinue
     */
    public CommandNextSolution getCommandContinue() {
        return this.commandNextSolution;
    }

    /**
     * Returns the initialized cancel command
     *
     * @return CommandCancel
     */
    public CommandCancel getCommandCancel() {
        return this.commandCancel;
    }
    
    /**
     * Returns the show about command
     * 
     * @return CommandShowAbout
     */
    public CommandShowAbout getCommandShowAbout() {
        return this.commandShowAbout;
    }

    /**
     * Returns the show standard library command
     * 
     * @return CommandShowStandard
     */
    public CommandShowStandard getCommandShowStandard() {
        return this.commandShowStandard;
    }
    
    /**
     * Returns the show manual command
     * 
     * @return CommandShowManual
     */
    public CommandShowManual getCommandShowManual() {
        return this.commandShowManual;
    }
     
    /**
     * Returns the zoom in command
     * 
     * @return CommandZoom (zoom-in-version)
     */
    public CommandZoom getCommandZoomTextIn() {
        return this.commandZoomTextIn;
    }

    /**
     * Returns the zoom out command
     * 
     * @return CommandZoom (zoom-out-version)
     */
    public CommandZoom getCommandZoomTextOut() {
        return this.commandZoomTextOut;
    }
}
