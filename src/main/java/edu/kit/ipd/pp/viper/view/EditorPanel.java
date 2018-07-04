package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

public class EditorPanel extends JPanel {
    private boolean changed;

    /**
     * 
     */
    public EditorPanel() {
        this.changed = false;

        this.setLayout(new BorderLayout());

        RSyntaxTextArea textArea = new RSyntaxTextArea();
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @return boolean
     */
    public boolean hasChanged() {
        return this.changed;
    }

    /**
     * @return String
     */
    public String getSourceText() {
        // TODO
        return "";
    }

    /**
     * @param text
     * @return
     */
    public void setSourceText(String text) {
        // TODO
    }

    /**
     * @param status
     * @return
     */
    public void setHasChanged(boolean status) {
        this.changed = status;
    }

    /**
     * @return
     */
    public boolean hasFileReference() {
        return false;
    }

    /**
     * @return
     */
    public File getFileReference() {
        return null;
    }
}
