package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.kit.ipd.pp.viper.controller.InterpreterManager;
import edu.kit.ipd.pp.viper.controller.CommandExit;

public class MainWindow extends JFrame {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -5807530819617746945L;

    /**
     * Stores whether VIPER was started in debug mode or not.
     * Debug mode will print some additional information to the console.
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
     * Default window dimensions (however window is resizeable)
     */
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;

    /**
     * Instances of all three panels
     */
    private final EditorPanel editorPanel;
    private final ConsolePanel consolePanel;
    private final VisualisationPanel visualisationPanel;

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
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(true);
        this.setIconImage(new ImageIcon(this.getClass().getResource(WINDOW_ICON)).getImage());

        // use system built-in look and feel instead of default swing look
        this.setDesign();

        this.editorPanel = new EditorPanel();
        this.consolePanel = new ConsolePanel(this);
        this.visualisationPanel = new VisualisationPanel(this);
        this.manager = new InterpreterManager();

        // add menu bar and tool bar to window
        this.setJMenuBar(new MenuBar(this));
        this.getContentPane().add(new ToolBar(this), BorderLayout.NORTH);

        // set up layout using two split panes
        this.initLayout();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // DEBUG (visualisation not implemented yet)
        this.getVisualisationPanel().setFromGraph(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            new CommandExit(getConsolePanel(), getEditorPanel()).execute();
         }
      });
    }

    /**
     * Sets the "look and feel" of the application by using the system default theme.
     */
    private void setDesign() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    /**
     * Sets up the main layout of the program. Two nested {@link JSplitPane}s are used to realise the wanted layout.
     */
    private void initLayout() {
        // the inner pane splits up the right side into top (visualisation) and bottom (console)
        JSplitPane innerPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                true,
                this.visualisationPanel,
                this.consolePanel
        );
        innerPane.setResizeWeight(0.5);
        innerPane.setDividerLocation(this.getWidth() / 2);
        innerPane.setEnabled(true);
        innerPane.setDividerSize(5);

        // the outer pane splits up the window into a left (editor) and right (inner pane) side
        JSplitPane outerPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                true,
                this.editorPanel,
                innerPane
        );
        outerPane.setResizeWeight(0.5);
        outerPane.setDividerLocation(this.getWidth() / 2);
        outerPane.setEnabled(false);
        outerPane.setDividerSize(0);

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
}
