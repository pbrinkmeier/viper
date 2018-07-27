package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ActionMap;

import org.apache.batik.bridge.UserAgent;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.AbstractPanInteractor;
import org.apache.batik.swing.gvt.Interactor;

import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

/**
 * Represents an interactive SVG viewer, customized for the needs of this
 * program.
 */
public class VisualisationViewer extends JSVGCanvas implements MouseWheelListener {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 8240189444679062587L;

    /**
     * Name of temp file to save the graph to
     */
    private static final String TMP_NAME = "viper_tmp.svg";

    /**
     * Reference of main window
     */
    private final MainWindow main;

    /**
     * Creates a new interactive viewer
     * 
     * @param gui Reference to main window
     */
    public VisualisationViewer(MainWindow gui) {
        super();

        this.setLayout(new BorderLayout());
        this.main = gui;

        // disable default rotation and panning iteractors
        this.setEnableImageZoomInteractor(false);
        this.setEnableRotateInteractor(false);
        this.setEnablePanInteractor(false);

        // add new iteractor that moves the image when left-clicking and holding the
        // mouse
        Interactor panInteractor = new AbstractPanInteractor() {
            @Override
            public boolean startInteraction(InputEvent ie) {
                int mods = ie.getModifiers();
                return ie.getID() == MouseEvent.MOUSE_PRESSED && (mods & InputEvent.BUTTON1_MASK) != 0;
            }
        };
        this.getInteractors().add(panInteractor);

        this.addMouseWheelListener(this);
    }

    /**
     * Implements zooming of the displayed image using the mouse wheel
     * 
     * @param event mouse wheel event that occurred
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        double mouseWheel = event.getPreciseWheelRotation();
        if (mouseWheel > 0.0)
            this.zoom(ZoomType.ZOOM_OUT);
        else
            this.zoom(ZoomType.ZOOM_IN);
    }

    /**
     * Performs a zoom operation
     * 
     * @param type Zoom type (in or out)
     */
    public void zoom(ZoomType type) {
        ActionMap map = this.getActionMap();
        double scale;

        switch (type) {
        case ZOOM_IN:
            // scale by factor 1.15, which seems to  be the sweet spot
            scale = 1.15;
            break;
        case ZOOM_OUT:
            // scale by the exact opposite of ZOOM_IN
            scale = 1/1.15;
            break;
        default:
            scale = 1;
            break;
        }

        (new JSVGCanvas.ZoomAction(scale)).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    /**
     * Clears the displayed SVG
     */
    public void clear() {
        this.setURI(null);
    }

    /**
     * Sets the displayed Graph. A previously shown graph will be cleared.
     * 
     * @param graph The graph to show
     */
    public void setFromGraph(Graph graph) {
        this.clear();

        String tmp = VisualisationViewer.getTempDir();
        if (tmp.equals("")) {
            this.main.getConsolePanel().printLine("Could not access tmp directory", LogType.DEBUG);
            return;
        }

        File tmpFile = new File(tmp + "/" + VisualisationViewer.TMP_NAME);
        try {
            Graphviz.fromGraph(graph).render(Format.SVG).toFile(tmpFile);
            this.main.getConsolePanel().printLine("Saved graph to " + tmpFile.getAbsolutePath(), LogType.DEBUG);
        } catch (IOException e) {
            this.main.getConsolePanel().printLine("Couldn't save graph to " + tmpFile.getAbsolutePath(), LogType.DEBUG);
            return;
        }

        this.setURI(tmpFile.toURI().toString());
    }

    /**
     * Returns the path to the temp directory of this system
     * 
     * @return Path to temp directory
     */
    private static String getTempDir() {
        String dir;

        try {
            dir = System.getProperty("java.io.tmpdir");
            if (dir == null)
                dir = "";
        } catch (SecurityException e) {
            return "";
        }

        return dir;
    }

    /**
     * This method must be overriden to use our custom user agent. That user agent
     * is used to suppress SVG warnings, because the default behavior is to display
     * a dialog instead of throwing an exception.
     */
    @Override
    protected UserAgent createUserAgent() {
        return new CustomUserAgent(this);
    }

    private class CustomUserAgent extends BridgeUserAgent {
        private VisualisationViewer viewer;

        public CustomUserAgent(VisualisationViewer viewer) {
            this.viewer = viewer;
        }

        @Override
        public void displayError(Exception exception) {
            this.displayError(exception.getMessage());
        }

        @Override
        public void displayError(String message) {
            this.viewer.main.getConsolePanel().printLine(String.format("SVG Render Error: %s", message), LogType.DEBUG);
        }
    }
}
