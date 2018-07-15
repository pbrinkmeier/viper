package edu.kit.ipd.pp.viper.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import javax.swing.JFileChooser;

import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

/**
 * Command for opening a file and writing its contents to the editor. This also
 * sets a reference to the file so future saving operations can overwrite this
 * file.
 */
public class CommandOpen extends Command {
    private ConsolePanel console;
    private EditorPanel editor;
    private VisualisationPanel visualisation;
    private Consumer<ClickableState> toggleStateFunc;
    private Consumer<String> setTitle;

    /**
     * Initializes a new open command.
     * 
     * @param console Panel of the console area
     * @param editor Panel of the editor area
     * @param visualisation Panel of the visualisation area
     * @param setTitle Consumer function that can change the window title
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *            elements in the GUI
     */
    public CommandOpen(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation,
            Consumer<String> setTitle, Consumer<ClickableState> toggleStateFunc) {
        this.console = console;
        this.editor = editor;
        this.visualisation = visualisation;
        this.toggleStateFunc = toggleStateFunc;
        this.setTitle = setTitle;
    }

    /**
     * File to String reading routine. This should only be called internally, but
     * it's public for testing purposes.
     * 
     * @param file the file to be read
     * @return the file content as a string
     */
    public String getFileText(File file) {
        StringBuffer buf = new StringBuffer();

        try {
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String str = "";
            while ((str = reader.readLine()) != null) {
                buf.append(str + '\n');
            }

            in.close();
            reader.close();
        } catch (IOException e) {
            printOpenError(e, file.getAbsolutePath());
        }

        return buf.toString();
    }

    /**
     * Reading error output routine. This should only be called internally, but it's
     * public for testing purposes.
     * 
     * @param e the exception that was raised by the reading failure
     * @param filePath the path of the file that couldn't be read
     */
    public void printOpenError(IOException e, String filePath) {
        String err = LanguageManager.getInstance().getString(LanguageKey.OPEN_FILE_ERROR);
        this.console.printLine(err + ": " + filePath, LogType.ERROR);

        if (MainWindow.inDebugMode()) {
            e.printStackTrace();
        }
    }

    /**
     * UI update routine. This should only be called internally, but it's public for
     * testing purposes.
     * 
     * @param file the file that was read
     */
    public void updateUI(File file) {
        this.editor.setSourceText(getFileText(file));
        this.editor.setHasChanged(false);
        this.editor.setFileReference(file);
        this.visualisation.clearVisualization();

        final String out = LanguageManager.getInstance().getString(LanguageKey.OPEN_FILE_SUCCESS);
        this.console.clearAll();
        this.console.printLine(out + ": " + file.getAbsolutePath(), LogType.INFO);

        this.toggleStateFunc.accept(ClickableState.NOT_PARSED_YET);
        this.setTitle.accept(file.getAbsolutePath());
    }

    /**
     * Executes the command.
     */
    public void execute() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(FileFilters.PL_FILTER);
        int rv = chooser.showOpenDialog(null);

        if (rv == JFileChooser.APPROVE_OPTION) {
            updateUI(chooser.getSelectedFile());
        }
    }
}
