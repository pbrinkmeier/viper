package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.kit.ipd.pp.viper.controller.CommandExit;
import edu.kit.ipd.pp.viper.controller.InterpreterManager;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;
import edu.kit.ipd.pp.viper.controller.PreferencesManager;

public class MainWindow extends JFrame {
    /**
     * VIPER version number
     * 
     * The version number follows the MAJOR.MINOR scheme, change this on each new release!
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

    private ToolBar toolbar;
    private MenuBar menubar;

    /**
     * Preferences manager instance
     */
    private final PreferencesManager prefManager;
    private Consumer<String> setWindowTitle;

    /**
     * Global instance of InterpreterManager
     */
    private InterpreterManager manager;

    /**
     * The constructor sets up the {@link JFrame} and initialises all three panels
     * 
     * @param debug Sets debug mode to enabled/disabled
     */
    public MainWindow(boolean debug) {
        MainWindow.debug = debug;

        this.setTitle(WINDOW_TITLE);
        this.setResizable(true);
        this.setIconImage(new ImageIcon(this.getClass().getResource(WINDOW_ICON)).getImage());

        // use system built-in look and feel instead of default swing look
        this.setDesign();

        this.manager = new InterpreterManager();
        this.visualisationPanel = new VisualisationPanel(this);
        this.consolePanel = new ConsolePanel(this);
        this.editorPanel = new EditorPanel();
        
        this.prefManager = new PreferencesManager(this.consolePanel);
        this.setWindowTitle = this::setWindowTitle;

        // add menu bar and tool bar to window
        this.menubar = new MenuBar(this);
        this.toolbar = new ToolBar(this);

        this.setJMenuBar(this.menubar);
        this.getContentPane().add(this.toolbar, BorderLayout.NORTH);

        // set up layout using two split panes
        this.initLayout();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // use CommandExit on JFrame close, because the editor may still containt
        // unsaved content
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new CommandExit(consolePanel, editorPanel, setWindowTitle).execute();
            }
        });
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        this.switchClickableState(ClickableState.NOT_PARSED_YET);

        // load language from config and set it
        LanguageManager.getInstance().setLocale(this.prefManager.getLanguage());
        this.consolePanel.printLine(String.format(LanguageManager.getInstance().getString(LanguageKey.VIPER_READY),
                VERSION), LogType.INFO);
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
    private void setDesign() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
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
     * Sets the window title to '{title} - VIPER'. This can be used to include the
     * currently opened file in the title.
     * 
     * @param title String to display, using "" or null removes the title
     */
    public void setWindowTitle(String title) {
        if (title == null || title.equals(""))
            this.setTitle(WINDOW_TITLE);
        else
            this.setTitle(title + " - " + WINDOW_TITLE);
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
}
