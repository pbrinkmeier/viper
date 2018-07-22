package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private static final int FONT_MAX_SIZE = 40;

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
     * Main window reference
     */
    private MainWindow main;

    /**
     * Indicates whether the document has changed since the last parsing
     */
    private boolean changed;

    /**
     * Holds a reference to the file that the editor content was loaded from
     */
    private File reference;
    
    /**
     * List of files referenced in the past
     */
    private ArrayList<File> referenceList;

    /**
     * Creates a new panel containing a text area with scroll support.
     *
     * @param gui Main window reference
     */
    public EditorPanel(MainWindow gui) {
        this.main = gui;

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

        this.referenceList = gui.getPreferencesManager().getFileReferences();
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
        if (this.changed && !this.main.getConsolePanel().hasLockedInput()) {
            this.main.getConsolePanel().lockInput();
            this.main.switchClickableState(ClickableState.NOT_PARSED_YET);
        }
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
        if (reference == null)
            return;
        
        this.reference = reference;

        if (!this.referenceList.contains(reference)) {
            // Trim the list down to 4 elements if necessary before
            // adding another item.
            if (this.referenceList.size() > 4) {
                this.referenceList.remove(0);
            }
            this.referenceList.add(reference);
            this.main.getInternalMenuBar().resetRecentlyOpenedMenu();
        } else {
            // Rearrange items so the requested item is at the back of the list.
            // This makes the least recently used file the first one to be deleted
            // if the list grows too big.
            ArrayList<File> copy = new ArrayList<File>(this.referenceList.size());
            int index = 0;
            for (int i = 0; i < this.referenceList.size(); i++) {
                if (this.referenceList.get(i).equals(reference))
                    index = i;
                else
                    copy.add(this.referenceList.get(i));
            }

            copy.add(this.referenceList.get(index));
            this.referenceList = copy;
            this.main.getInternalMenuBar().resetRecentlyOpenedMenu();
        }

        this.main.getPreferencesManager().setFileReferences(Collections.unmodifiableList(this.referenceList));
    }

    /**
     * Getter-Method for the list of file references.
     * 
     * @return the list of file references
     */
    public List<File> getFileReferenceList() {
        return Collections.unmodifiableList(this.referenceList);
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
        this.setHasChanged(true);
    }

    /**
     * Called when a portion of text was removed from the text area.
     */
    @Override
    public void removeUpdate(DocumentEvent event) {
        setHasChanged(true);
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

    private void resetFont() {
        this.fontSize = FONT_DEFAULT_SIZE;
        this.textArea.setFont(new Font("Monospaced", Font.PLAIN, this.fontSize));
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
            event.consume();
            break;
        case KeyEvent.VK_MINUS:    // minus key
        case KeyEvent.VK_SUBTRACT: // numpad minus key
            this.decreaseFont();
            event.consume();
            break;
        case KeyEvent.VK_0: // 0 key
            this.resetFont();
            event.consume();
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
