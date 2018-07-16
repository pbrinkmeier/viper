package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
public class EditorPanel extends JPanel implements DocumentListener, KeyListener, MouseWheelListener {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 689492118433496287L;

    private static final int FONT_DEFAULT_SIZE = 14;
    private static final int FONT_MIN_SIZE = 10;
    private static final int FONT_MAX_SIZE = 30;

    private int fontSize;

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
        this.fontSize = FONT_DEFAULT_SIZE;

        this.textArea = new RSyntaxTextArea();
        this.textArea.setFont(new Font("Monospaced", Font.PLAIN, FONT_DEFAULT_SIZE));
        this.textArea.setTabSize(2);
        this.textArea.setTabsEmulated(true);
        this.textArea.addKeyListener(this);
        this.textArea.addMouseWheelListener(this);
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
     * @param changed Boolean value describing whether the editor has unsaved
     *            content
     */
    public void setHasChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Checks whether the editor has a reference to a {@link File} the content was
     * opened from
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
     * Called when an attribute of the {@link Document} has changed. We only care
     * about changes to the actual text, therefore nothing happens here.
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

    private void increaseFont() {
        if (this.fontSize > FONT_MAX_SIZE)
            return;

        this.textArea.setFont(new Font("Monospaced", Font.PLAIN, ++this.fontSize));
    }

    private void decreaseFont() {
        if (this.fontSize < FONT_MIN_SIZE)
            return;

        this.textArea.setFont(new Font("Monospaced", Font.PLAIN, --this.fontSize));
    }

    /**
     * Fired when a key was pressed
     * 
     * @param event Event that happened
     */
    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (!event.isControlDown())
            return;

        switch (keyCode) {
        case KeyEvent.VK_PLUS: // plus key
        case KeyEvent.VK_ADD:  // numpad plus key
            this.increaseFont();
            break;
        case KeyEvent.VK_MINUS:    // minus key
        case KeyEvent.VK_SUBTRACT: // numpad minus key
            this.decreaseFont();
            break;
        default:
            break;
        }
    }

    /**
     * Fired when a key was successfully typed, ignored here
     * 
     * @param e The issued event
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Fire when a key was released, ignored here
     * 
     * @param e The issued event
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Fired when mouse was scrolled
     * 
     * @param event The event that happened
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (!event.isControlDown())
            return;

        if (event.getPreciseWheelRotation() > 0.0)
            this.decreaseFont();
        else
            this.increaseFont();
    }
}
