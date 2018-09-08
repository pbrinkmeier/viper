package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

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
     * The default zoom of the visualisation
     */
    public static final double DEFAULT_ZOOM = 1.0;
    
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 8240189444679062587L;

    /**
     * Name of temp file to save the graph to
     */
    private static final String TMP_NAME = "viper_tmp.svg";

    /**
     * Factor for scaling, 12.0 seems to be the sweet spot
     */
    private static final double ZOOM_FACTOR = 12.0;
    
    /**
     * The minimum scale value
     */    
    private static final double MIN_ZOOM = 0.01;
    
    /**
     * The scale to which the visualisation is currently zoomed
     */
    private static double scale = VisualisationViewer.DEFAULT_ZOOM;

    /**
     * Reference of main window
     */
    private final MainWindow main;
    
    /**
     * Boolean flag deciding whether the viewer can be navigated
     */
    private boolean navigationEnabled = true;

    /**
     * Reference to the current graph
     */
    private Graph currentGraph;
    
    /**
     * Creates a new interactive viewer
     * 
     * @param gui Reference to main window
     */
    @SuppressWarnings("unchecked")
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
                return ie.getID() == MouseEvent.MOUSE_PRESSED
                                  && (mods & InputEvent.BUTTON1_MASK) != 0
                                  && VisualisationViewer.this.navigationEnabled;
            }
        };

        this.getInteractors().add(panInteractor);
        this.addMouseWheelListener(this);
    }

    /**
     * Returns the scale factor by which the visualisation is zoomed
     * 
     * @return the scale factor
     */
    public double getScale() {
        return VisualisationViewer.scale;
    }
    
    /**
     * Implements zooming of the displayed image using the mouse wheel
     * 
     * @param event mouse wheel event that occurred
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        final double stepSize = 0.2;
        
        if (this.navigationEnabled) {
            AffineTransform at = this.getRenderingTransform();
            
            Point2D src = event.getPoint();
            Point2D dest = null;
            try {
                dest = at.inverseTransform(src, null);
            } catch (NoninvertibleTransformException e) {
                if (MainWindow.inDebugMode()) {
                    e.printStackTrace();
                }
            }
            
            double input = (stepSize * event.getPreciseWheelRotation());
            double step = VisualisationViewer.calculateStep(input);
            VisualisationViewer.updateScaleValue(-step);
            
            at.setToIdentity();
            at.translate(src.getX(), src.getY());
            at.scale(VisualisationViewer.scale, VisualisationViewer.scale);
            at.translate(-dest.getX(), -dest.getY());
            this.setRenderingTransform(at, true);
        }
    }
    
    /**
     * Performs a zoom operation
     * 
     * @param type Zoom type (in or out)
     */
    public void zoom(ZoomType type) {
        AffineTransform at = this.getRenderingTransform();

        Point2D src = new Point((int) this.getSize().getWidth() / 2, (int) this.getSize().getHeight() / 2);
        Point2D dest = null;
        try {
            dest = at.inverseTransform(src, null);
        } catch (NoninvertibleTransformException e) {
            if (MainWindow.inDebugMode()) {
                e.printStackTrace();
            }
        }
        
        double input = (type == ZoomType.ZOOM_IN ? VisualisationViewer.ZOOM_FACTOR : -VisualisationViewer.ZOOM_FACTOR);
        double step = VisualisationViewer.calculateStep(input);
        
        VisualisationViewer.updateScaleValue(step);
        at.setToIdentity();
        at.translate(src.getX(), src.getY());
        at.scale(VisualisationViewer.scale, VisualisationViewer.scale);
        at.translate(-dest.getX(), -dest.getY());
        this.setRenderingTransform(at, true);
    }
    
    private static double calculateStep(double input) {
        int sign = input >= 0.0 ? 1 : -1;
        return sign * (Math.abs(input / 10.0 - VisualisationViewer.scale) * 0.05);
    }
    
    private static void updateScaleValue(double step) {
        VisualisationViewer.scale += step;
        VisualisationViewer.scale = Math.max(VisualisationViewer.scale, VisualisationViewer.MIN_ZOOM);
    }
    
    /**
     * Resets the zoom in the visualisation area
     */
    public void resetZoom() {
        this.setFromGraph(this.currentGraph);
        VisualisationViewer.scale = VisualisationViewer.DEFAULT_ZOOM;
    }

    /**
     * Clears the displayed SVG
     */
    public void clear() {
        try {
            this.setURI(null);            
        } catch (NullPointerException e) {
            ConsolePanel panel = this.main.getConsolePanel();
            if (panel != null) {
                panel.printLine("Failed to clear visualisation. This should only happen during testing as"
                        + "a result of bad threading in Batik", LogType.DEBUG);
            }
        }
    }

    /**
     * Sets the displayed Graph. A previously shown graph will be cleared.
     * 
     * @param graph The graph to show
     */
    public void setFromGraph(Graph graph) {
        this.currentGraph = graph;

        String tmp = VisualisationViewer.getTempDir();
        if (tmp.equals("")) {
            this.main.getConsolePanel().printLine("Could not access tmp directory", LogType.DEBUG);
            return;
        }

        ConsolePanel console = this.main.getConsolePanel();
        File tmpFile = new File(tmp + "/" + VisualisationViewer.TMP_NAME);
        final String path = tmpFile.getAbsolutePath();
        try {
            Graphviz.fromGraph(graph).render(Format.SVG).toFile(tmpFile);

            if (console != null) {
                this.main.getConsolePanel().printLine("Saved graph to " + path, LogType.DEBUG);
            }
        } catch (IOException e) {
            if (console != null) {
                this.main.getConsolePanel().printLine("Couldn't save graph to " + path, LogType.DEBUG);
            }
            return;
        }

        this.loadSVGDocument(tmpFile.toURI().toString());
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
            if (dir == null) {
                dir = "";
            }
        } catch (SecurityException e) {
            return "";
        }

        return dir;
    }

    /**
     * Enables the navigation (zooming and scrolling)
     */
    public void enableNavigation() {
        this.navigationEnabled = true;
    }

    /**
     * Disables the navigation (zooming and scrolling)
     */
    public void disableNavigation() {
        this.navigationEnabled = false;        
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
    
    @Override
    public void setCursor(Cursor cursor) {
        // do nothing
    }

    /**
     * Reimplementation of {@link JSVGCanvas#setCursor(Cursor)} to avoid SVG related custom cursors
     * @param cursor cursor to set
     */
    public void setCustomCursor(Cursor cursor) {
        super.setCursor(cursor);
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
