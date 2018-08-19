package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.kit.ipd.pp.viper.controller.CommandParseQuery;
import edu.kit.ipd.pp.viper.controller.PreferencesManager;
import edu.kit.ipd.pp.viper.controller.ZoomType;

/**
 * Represents a panel containing a console-like output field, as well as an
 * input field for prolog queries.
 */
public class ConsolePanel extends JPanel implements HasClickable {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -533194077782445895L;

    /**
     * Main window reference
     */
    private MainWindow main;

    /**
     * Scroll pane
     */
    private JScrollPane scrollPane;

    /**
     * Console panel
     */
    private ConsoleOutputArea outputArea;

    /**
     * Input field panel
     */
    private ConsoleInputField inputField;
    private boolean inputLocked;

    /**
     * Creates a new console panel
     * 
     * @param gui Main window reference
     */
    public ConsolePanel(MainWindow gui) {
        super();

        this.main = gui;

        this.setPreferredSize(new Dimension(400, 200));
        this.setLayout(new BorderLayout());

        this.outputArea = new ConsoleOutputArea();
        JPanel noWrapPanel = new JPanel();
        noWrapPanel.add(this.outputArea);
        this.scrollPane = new JScrollPane(noWrapPanel);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setViewportView(this.outputArea);

        this.inputField = new ConsoleInputField(new CommandParseQuery(this, this.main.getVisualisationPanel(),
                this.main.getInterpreterManager(), this.main::switchClickableState));

        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.inputField, BorderLayout.PAGE_END);
        this.inputLocked = true;
    }
    
    /**
     * Sets the preferences manager for the console output area.
     * This can't be done in the constructor since there is a cyclic dependency
     * between the console and the preferences manager. This should be called
     * directly after the init of the preferences manager.
     * 
     * @param preferencesManager The preferences manager to be set
     */
    public void setPreferencesManager(PreferencesManager preferencesManager) {
        this.outputArea.setPreferencesManager(preferencesManager);
    }

    /**
     * Clears the console and the input field
     */
    public void clearAll() {
        this.outputArea.clear();
        this.inputField.clear();
    }

    /**
     * Returns the content of the input field
     * 
     * @return String Input field contents
     */
    public String getInputFieldText() {
        return this.inputField.getText();
    }

    /**
     * Returns the content of the output area
     * 
     * @return String Output area contents
     */
    public String getOutputAreaText() {
        return this.outputArea.getText();
    }

    /**
     * Returns whether the input field is locked
     * 
     * @return boolean value describing whether the input field is locked
     */
    public boolean hasLockedInput() {
        return this.inputLocked;
    }

    /**
     * Makes the input field un-editable
     */
    public void lockInput() {
        this.inputField.clear();
        this.inputField.lock();
        this.inputLocked = true;
    }

    /**
     * Makes the input field editable
     */
    public void unlockInput() {
        this.inputField.unlock();
        this.inputLocked = false;
    }

    /**
     * Clears the input field
     */
    public void clearInputField() {
        this.inputField.clear();
    }

    /**
     * Clears the console
     */
    public void clearOutputArea() {
        this.outputArea.clear();
    }
    
    /**
     * Zooms inside the output area
     * 
     * @param type The direction to zoom in
     */
    public void zoomOutputArea(ZoomType type) {
        if (type == ZoomType.ZOOM_IN)
            this.outputArea.increaseFont();
        else
            this.outputArea.decreaseFont();
    }
    
    /**
     * Resets the zoom in the output area
     */
    public void resetZoom() {
        this.outputArea.resetFont();
    }

    /**
     * Prints a new line (including a line break) to the console. The
     * {@link LogType} determines the line color. {@link LogType#DEBUG} will only be
     * printed if the program was started in debug mode.
     * 
     * @param line The String to print
     * @param type Type of message
     */
    public void printLine(String line, LogType type) {
        this.outputArea.printLine(line, type);

        JScrollBar bar = this.scrollPane.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    }

    /**
     * Called when program switched to a new state (program was parsed, query was
     * send, ...)
     */
    @Override
    public void switchClickableState(ClickableState state) {
        this.inputField.switchClickableState(state);
    }
}
