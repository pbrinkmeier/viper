package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Represents a panel containing an editor
 */
public class EditorPanel extends JPanel implements DocumentListener {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 689492118433496287L;

    /**
     * The actual text area
     */
    private final RSyntaxTextArea textArea;

    /**
     * A pane surrounding the text area to allow scrolling
     */
    private final RTextScrollPane scrollPane;

    /**
     * Indicates whether the document has changed since the last parsing
     */
    private boolean changed;

    /**
     * Holds a reference to the file that the editor content was loaded from
     */
    private File reference;

    /**
     * Creates a new panel containing a text area with scroll support.
     */
    public EditorPanel() {
        this.changed = false;

        this.setLayout(new BorderLayout());
        this.setFont(new Font("Monospaced", Font.PLAIN, 14));

        this.textArea = new RSyntaxTextArea();
        this.scrollPane = new RTextScrollPane(this.textArea);
        this.scrollPane.setPreferredSize(new Dimension(400, 600));
        this.add(this.scrollPane, BorderLayout.CENTER);
        
        this.textArea.getDocument().addDocumentListener(this);
    }

    /**
     * Returns whether the text content has changed since the last parsing
     * 
     * @return boolean true if has changed, false otherwise
     */
    public boolean hasChanged() {
        return this.changed;
    }

    /**
     * Returns the current content of the text area
     * 
     * @return Text area content
     */
    public String getSourceText() {
        try {
            return this.textArea.getText();
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * Sets the content of the text area. All previous content is overridden.
     * 
     * @param text The new content
     */
    public void setSourceText(String text) {
        this.textArea.setText(text);
    }

    /**
     * Sets the internal status whether the editor has unsaved changes.
     * 
     * @param changed Boolean value describing whether the editor has unsaved content
     */
    public void setHasChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Checks whether the editor has a reference to a {@link File} the content was opened from
     * 
     * @return true if a {@link File} reference is known, false otherwise
     */
    public boolean hasFileReference() {
        return this.reference != null;
    }

    /**
     * Returns reference to a file the editor content was loaded from.
     * 
     * @return file reference, null if {@link #hasFileReference()} returns false
     */
    public File getFileReference() {
        return this.reference;
    }

    /**
     * Sets the file reference the editor content was loaded from
     * 
     * @param reference File reference
     */
    public void setFileReference(File reference) {
        this.reference = reference;
    }

    /**
     * Called when an attribute of the {@link Document} has changed.
     * We only care about changes to the actual text, therefore nothing happens here.
     */
    @Override
    public void changedUpdate(DocumentEvent event) {
    }

    /**
     * Called when a portion of text was inserted into the text area.
     */
    @Override
    public void insertUpdate(DocumentEvent event) {
        this.changed = true;
    }

    /**
     * Called when a portion of text was removed from the text area.
     */
    @Override
    public void removeUpdate(DocumentEvent event) {
        this.changed = true;
    }
}
