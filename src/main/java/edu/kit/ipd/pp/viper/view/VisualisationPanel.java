package edu.kit.ipd.pp.viper.view;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import edu.kit.ipd.pp.viper.controller.CommandZoom;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;
import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

/**
 * Represents a panel containing a SVG viewer
 */
public class VisualisationPanel extends JPanel implements ComponentListener, HasClickable, Observer {
    /**
     * The default zoom level of the visualisation panel
     */
    public static final int DEFAULT_ZOOM_LEVEL = 0;
    
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 6723362475925553655L;

    /**
     * Icons for zoom buttons
     */
    private static final String ICON_ZOOM_IN = "/icons_png/icon_zoom_in.png";
    private static final String ICON_ZOOM_OUT = "/icons_png/icon_zoom_out.png";
    
    /**
     * The zoom level of the visualisation. This is an indicator of whether
     * the visualisation is currently zoomed in (positive), out (negative)
     * or not zoomed at all (zero).
     */
    private int zoomLevel = VisualisationPanel.DEFAULT_ZOOM_LEVEL;
    
    private ToolBarButton zoomIn;
    private ToolBarButton zoomOut;
    
    /**
     * Main window
     */
    private final MainWindow main;

    /**
     * Viewer to use
     */
    private final VisualisationViewer viewer;
    
    private boolean showsPlaceholder;
    private Graph placeholderGraph;
    
    /**
     * Creates a new viewer panel
     * 
     * @param gui Reference of main class
     */
    public VisualisationPanel(MainWindow gui) {
        super();

        this.main = gui;
        this.addComponentListener(this);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(400, 400));
        this.setBackground(ColorScheme.GUI_WHITE);

        // use a layered pane here, so that the zoom buttons can float above
        // the visualisation viewer
        JLayeredPane contentPane = new JLayeredPane();
        contentPane.setPreferredSize(new Dimension(400, 400));

        this.viewer = new VisualisationViewer(this.main);
        this.componentResized(null);
        
        this.zoomIn = new ToolBarButton(GUIComponentID.BUTTON_ZOOM_IN,
                VisualisationPanel.ICON_ZOOM_IN,
                LanguageKey.ZOOM_IN, new CommandZoom(this, null, null, ZoomType.ZOOM_IN));

        this.zoomOut = new ToolBarButton(GUIComponentID.BUTTON_ZOOM_OUT,
                        VisualisationPanel.ICON_ZOOM_OUT,
                        LanguageKey.ZOOM_OUT, new CommandZoom(this, null, null, ZoomType.ZOOM_OUT));
        
        this.zoomIn.setBounds(10, 10, 30, 30);
        this.zoomOut.setBounds(10, 40, 30, 30);


        // viewer is on level 1, both buttons on level 2 and therefore appear above the
        // viewer
        contentPane.add(this.viewer, new Integer(1));
        contentPane.add(this.zoomIn, new Integer(2));
        contentPane.add(this.zoomOut, new Integer(2));

        this.add(contentPane, BorderLayout.CENTER);

        this.buildPlaceholderGraph();
        this.setFromGraph(this.placeholderGraph);
        this.showsPlaceholder = true;
        
        LanguageManager.getInstance().addObserver(this);
    }
    
    /**
     * Returns the current zoom level.
     * 
     * @return the current zoom level
     */
    public int getZoomLevel() {
        return this.zoomLevel;
    }
    
    /**
     * Sets custom cursor for all VisualisationPanel subpanels
     * @param cursor cursor to set
     */
    public void setGlobalCursor(Cursor cursor) {
        this.setCursor(cursor);
        this.viewer.setCustomCursor(cursor);
    }

    /**
     * Returns whether there is a graph currently shown in the panel.
     * 
     * @return boolean value whether a graph is set right now
     */
    public boolean hasGraph() {
        return !this.showsPlaceholder;
    }
    
    /**
     * Returns whether the graph currently shown in the panel is the placeholder graph.
     * 
     * @return boolean value whether the set graph is the placeholder graph
     */
    public boolean showsPlaceholder() {
        return this.showsPlaceholder;
    }

    /**
     * Clears the currently shown visualisation
     */
    public void clearVisualization() {
        this.setFromGraph(this.placeholderGraph);
        this.showsPlaceholder = true;
    }

    /**
     * Performs a zoom operation on the currently shown graph
     * 
     * @param direction Direction to zoom (in or out)
     */
    public void zoom(ZoomType direction) {
        if (!this.showsPlaceholder) {
            this.viewer.zoom(direction);

            if (direction == ZoomType.ZOOM_IN) {
                this.zoomLevel++;
            } else if (direction == ZoomType.ZOOM_OUT) {
                this.zoomLevel--;
            }
        }
    }
    
    /**
     * Resets the zoom in the visualisation viewer
     */
    public void resetZoom() {
        this.viewer.resetZoom();
        this.zoomLevel = DEFAULT_ZOOM_LEVEL;
    }

    /**
     * Sets the displayed Graph. A previously shown graph will be cleared.
     * 
     * @param graph The graph to show
     */
    public void setFromGraph(Graph graph) {
        if (graph == null) {
            return;
        }

        this.viewer.setFromGraph(graph);
        this.showsPlaceholder = graph == this.placeholderGraph;
        this.resetZoom();
    }
    
    private void buildPlaceholderGraph() {
        Node rootNode = node(LanguageManager.getInstance().getString(LanguageKey.VISUALISATION_HINT)).with(
                Shape.RECTANGLE,
                ColorScheme.VIS_WHITE,
                ColorScheme.PLACEHOLDER_VIS_COLOR.font());
        this.placeholderGraph = graph("placeholder")
                .directed()
                .with(rootNode)
                .nodeAttr()
                .with(Font.name("Times New Roman"));
    }

    /**
     * Called when the visualisation panel gets resized
     * 
     * @param event Event that caused the resize, ignored here
     */
    @Override
    public void componentResized(ComponentEvent event) {
        this.viewer.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public void componentHidden(ComponentEvent arg0) {
        return;
    }

    @Override
    public void componentMoved(ComponentEvent arg0) {
        return;
    }

    @Override
    public void componentShown(ComponentEvent e) {
        return;
    }

    @Override
    public void switchClickableState(ClickableState state) {
        switch (state) {
        case NOT_PARSED_YET:
        case PARSED_PROGRAM:
            if (this.placeholderGraph == null) {
                this.buildPlaceholderGraph();
            }
            
            this.setFromGraph(this.placeholderGraph);
            this.viewer.disableNavigation();
            break;
        case PARSED_QUERY:
        case FIRST_STEP:
        case LAST_STEP:
        case NO_MORE_SOLUTIONS:
            this.viewer.enableNavigation();
            break;
        default:
            break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {        
        if (this.showsPlaceholder) {
            this.buildPlaceholderGraph();
            this.setFromGraph(this.placeholderGraph);
        }
    }
}
