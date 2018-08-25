package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
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
    private EditorPanel editorPanel;
    private ConsolePanel consolePanel;
    private VisualisationPanel visualisationPanel;

    private CommandNew commandNew;
    private CommandOpen commandOpen;
    private CommandSave commandSave;
    private CommandExit commandExit;
    private CommandParse commandParse;
    private CommandFormat commandFormat;
    private CommandPreviousStep commandPreviousStep;
    private CommandNextStep commandNextStep;
    private CommandNextSolution commandNextSolution;
    private CommandCancel commandCancel;
    private CommandShowAbout commandShowAbout;
    private CommandShowStandard commandShowStandard;
    private CommandZoom commandZoomTextIn;
    private CommandZoom commandZoomTextOut;
    private CommandShowManual commandShowManual;

    private ToolBar toolbar;
    private MenuBar menubar;

    /**
     * Preferences manager instance
     */
    private PreferencesManager prefManager;

    /**
     * Global instance of InterpreterManager
     */
    private InterpreterManager manager;
    
    /**
     * Global Window Title
     */
    private String windowTitle;

    /**
     * The global clickable state
     */
    private ClickableState clickableState;
    
    /**
     * The constructor sets up the {@link JFrame} and initialises all three panels.
     * 
     * To prevent threading issues with Swing, the entire constructor is wrapped in an invokeAndWait() method. This is
     * arguably a bit ugly, however running JUnit tests revealed that Swing does run the window initialization in a
     * separate thread, causing weird race conditions and producing NullPointer- and ConcurrentModificationExceptions.
     * 
     * When starting the program as a user, you'll probably never run into these Swing threading issues. However the
     * unit tests do create a MainWindow instance and immediately call getters an setters, relying on the MainWindow
     * initialization to be complete. The only way to garuantee that is to run everything inside a blocking
     * invokeAndWait() method.
     * 
     * @param debug Sets debug mode to enabled/disabled
     * @throws InterruptedException 
     * @throws InvocationTargetException 
     */
    public MainWindow(boolean debug) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    MainWindow.debug = debug;

                    MainWindow.this.setName(GUIComponentID.FRAME_MAIN.toString());
                    MainWindow.this.setTitle(MainWindow.WINDOW_TITLE);
                    MainWindow.this.setResizable(true);
                    MainWindow.this.setIconImage(new ImageIcon(this.getClass().getResource(MainWindow.WINDOW_ICON))
                            .getImage());

                    // use system built-in look and feel instead of default swing look
                    MainWindow.setDesign();

                    MainWindow.this.manager = new InterpreterManager(MainWindow.this::switchClickableState);
                    MainWindow.this.visualisationPanel = new VisualisationPanel(MainWindow.this);
                    MainWindow.this.consolePanel = new ConsolePanel(MainWindow.this);
                    MainWindow.this.prefManager = new PreferencesManager(MainWindow.this.consolePanel);
                    MainWindow.this.consolePanel.setPreferencesManager(MainWindow.this.prefManager);
                    MainWindow.this.editorPanel = new EditorPanel(MainWindow.this);

                    // Create command instances
                    MainWindow.this.commandSave = new CommandSave(MainWindow.this.consolePanel,
                            MainWindow.this.editorPanel, SaveType.SAVE, MainWindow.this::setWindowTitle,
                            MainWindow.this.manager);
                    MainWindow.this.commandOpen = new CommandOpen(MainWindow.this.consolePanel,
                            MainWindow.this.editorPanel, MainWindow.this.visualisationPanel,
                            MainWindow.this::setWindowTitle, MainWindow.this::switchClickableState,
                            MainWindow.this.commandSave, MainWindow.this.manager);
                    MainWindow.this.commandNew = new CommandNew(MainWindow.this.consolePanel,
                            MainWindow.this.editorPanel, MainWindow.this.visualisationPanel,
                            MainWindow.this::setWindowTitle, MainWindow.this::switchClickableState,
                            MainWindow.this.commandSave, MainWindow.this.manager);
                    MainWindow.this.commandParse = new CommandParse(MainWindow.this.consolePanel,
                            MainWindow.this.editorPanel, MainWindow.this.visualisationPanel, MainWindow.this.manager,
                            MainWindow.this::switchClickableState);
                    MainWindow.this.commandZoomTextIn = new CommandZoom(null, MainWindow.this.consolePanel,
                            MainWindow.this.editorPanel, ZoomType.ZOOM_IN);
                    MainWindow.this.commandZoomTextOut = new CommandZoom(null, MainWindow.this.consolePanel,
                            MainWindow.this.editorPanel, ZoomType.ZOOM_OUT);
                    MainWindow.this.commandFormat = new CommandFormat(MainWindow.this.consolePanel,
                            MainWindow.this.editorPanel);
                    MainWindow.this.commandPreviousStep = new CommandPreviousStep(MainWindow.this.visualisationPanel,
                            MainWindow.this.manager);
                    MainWindow.this.commandNextStep = new CommandNextStep(MainWindow.this.visualisationPanel,
                            MainWindow.this.manager, MainWindow.this.consolePanel);
                    MainWindow.this.commandNextSolution = new CommandNextSolution(MainWindow.this.consolePanel,
                            MainWindow.this.visualisationPanel, MainWindow.this.manager);
                    MainWindow.this.commandCancel = new CommandCancel(MainWindow.this.manager);
                    MainWindow.this.commandExit = new CommandExit(MainWindow.this.editorPanel,
                            MainWindow.this.commandSave, MainWindow.this.manager);
                    MainWindow.this.commandShowAbout = new CommandShowAbout();
                    MainWindow.this.commandShowStandard = new CommandShowStandard(MainWindow.this.manager);
                    MainWindow.this.commandShowManual = new CommandShowManual();

                    MainWindow.this.editorPanel.setZoomInCommand(MainWindow.this.commandZoomTextIn);
                    MainWindow.this.editorPanel.setZoomOutCommand(MainWindow.this.commandZoomTextOut);
                    
                    // add menu bar and tool bar to window
                    MainWindow.this.menubar = new MenuBar(MainWindow.this);
                    MainWindow.this.toolbar = new ToolBar(MainWindow.this);

                    MainWindow.this.setJMenuBar(MainWindow.this.menubar);
                    MainWindow.this.getContentPane().add(MainWindow.this.toolbar, BorderLayout.NORTH);

                    // set up layout using two split panes
                    MainWindow.this.initLayout();

                    MainWindow.this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    // use CommandExit on JFrame close, because the editor may still contain
                    // unsaved content
                    MainWindow.this.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            MainWindow.this.commandExit.execute();
                        }
                    });
                    MainWindow.this.setLocationRelativeTo(null);
                    MainWindow.this.pack();
                    MainWindow.this.setVisible(true);
                    MainWindow.this.switchClickableState(ClickableState.NOT_PARSED_YET);

                    // load language from config and set it
                    LanguageManager.getInstance().setLocale(MainWindow.this.prefManager.getLanguage());
                    MainWindow.this.consolePanel.printLine(
                            String.format(LanguageManager.getInstance().getString(LanguageKey.VIPER_READY),
                                    MainWindow.VERSION),
                            LogType.INFO);
                }
            });
        } catch (InvocationTargetException | InterruptedException e) {
            // this really isn't supposed to happen
        }
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
        this.clickableState = state;
    }
    
    /**
     * Returns the current global clickable state.
     * 
     * @return the global clickable state
     */
    public ClickableState getClickableState() {
        return this.clickableState;
    }
    
    /**
     * Sets a specific cursor on all submodules
     * @param cursor Cursor type to set
     */
    public void setGlobalCursor(Cursor cursor) {
        this.setCursor(cursor);
        this.menubar.setCursor(cursor);
        this.toolbar.setCursor(cursor);
        this.consolePanel.setGlobalCursor(cursor);
        this.visualisationPanel.setGlobalCursor(cursor);
        this.editorPanel.setGlobalCursor(cursor);
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
     * @param args Command line arguments. Pass '--debug' for debug mode.
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
