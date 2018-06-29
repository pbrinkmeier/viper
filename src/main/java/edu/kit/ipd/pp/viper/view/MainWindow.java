package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import edu.kit.ipd.pp.viper.controller.InterpreterManager;

public class MainWindow extends JFrame {
    /**
     * Window title
     */
    private final static String WINDOW_TITLE = "VIPER";

    /**
     * Window icon
     * 
     * The icon path has to begin with a slash,
     * otherwise Java will look up the file in the package folder, not in
     * src/main/resources
     */
    private final static String WINDOW_ICON = "/viper-icon.png";

    /**
     * Default window dimensions (however window is resizeable)
     */
    private final static int WINDOW_HEIGHT = 600;
    private final static int WINDOW_WIDTH  = 800;

    /**
     * Instances of all three panels
     */
    private EditorPanel editorPanel;
    private ConsolePanel consolePanel;
    private VisualisationPanel visualisationPanel;

    /**
     * Global instance of InterpreterManager
     */
    private InterpreterManager manager;

    /**
     * The constructor sets up the {@link JFrame} and initialises all three
     * panels
     */
    public MainWindow() {
        this.setTitle(WINDOW_TITLE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(true);
        this.setIconImage(new ImageIcon(getClass().getResource(WINDOW_ICON)).getImage());

        this.setJMenuBar(new MenuBar(this));

        Container contentPane = this.getContentPane();
        contentPane.add(new ToolBar(this), BorderLayout.NORTH);

        this.editorPanel = new EditorPanel();
        this.consolePanel = new ConsolePanel();
        this.visualisationPanel = new VisualisationPanel();

        this.manager = new InterpreterManager();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Main method, creates a new instance of this class
     * 
     * @param args Command line arguments (ignored)
     */
    public static void main(String[] args) {
        new MainWindow();
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
