package edu.kit.ipd.pp.viper.view;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

import java.awt.BorderLayout;
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
     * Serial UID
     */
    private static final long serialVersionUID = 6723362475925553655L;

    /**
     * Icons for zoom buttons
     */
    private static final String ICON_ZOOM_IN = "/icons_png/icon_zoom_in.png";
    private static final String ICON_ZOOM_OUT = "/icons_png/icon_zoom_out.png";

    /**
     * Main window
     */
    private final MainWindow main;

    /**
     * Viewer to use
     */
    private final VisualisationViewer viewer;

    private ToolBarButton zoomIn;
    private ToolBarButton zoomOut;

    private boolean hasGraph;
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

        this.zoomIn = new ToolBarButton(ICON_ZOOM_IN, LanguageKey.ZOOM_IN, new CommandZoom(this, ZoomType.ZOOM_IN));
        this.zoomIn.setBounds(10, 10, 30, 30);
        this.zoomOut = new ToolBarButton(ICON_ZOOM_OUT, LanguageKey.ZOOM_OUT, new CommandZoom(this, ZoomType.ZOOM_OUT));
        this.zoomOut.setBounds(10, 40, 30, 30);

        // viewer is on level 1, both buttons on level 2 and therefore appear above the
        // viewer
        contentPane.add(this.viewer, new Integer(1));
        contentPane.add(this.zoomIn, new Integer(2));
        contentPane.add(this.zoomOut, new Integer(2));

        this.add(contentPane, BorderLayout.CENTER);
        this.hasGraph = false;
        this.showsPlaceholder = true;
        
        LanguageManager.getInstance().addObserver(this);
    }

    /**
     * Returns whether there is a graph currently shown in the panel.
     * 
     * @return boolean value whether a graph is set right now
     */
    public boolean hasGraph() {
        return this.hasGraph;
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
        this.viewer.clear();
        this.hasGraph = false;
    }

    /**
     * Performs a zoom operation on the currently shown graph
     * 
     * @param direction Direction to zoom (in or out)
     */
    public void zoom(ZoomType direction) {
        this.viewer.zoom(direction);
    }

    /**
     * Sets the displayed Graph. A previously shown graph will be cleared.
     * 
     * @param graph The graph to show
     */
    public void setFromGraph(Graph graph) {
        if (graph == this.placeholderGraph)
            this.showsPlaceholder = true;
        
        this.clearVisualization();
        this.viewer.setFromGraph(graph);
        this.hasGraph = true;
    }
    
    private void buildPlaceholderGraph() {
        Node rootNode = node(LanguageManager.getInstance().getString(LanguageKey.VISUALISATION_HINT)).with(
                Shape.RECTANGLE,
                ColorScheme.PLACEHOLDER_COLOR,
                ColorScheme.PLACEHOLDER_COLOR.font());
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
    }

    @Override
    public void componentMoved(ComponentEvent arg0) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void switchClickableState(ClickableState state) {
        switch (state) {
        case NOT_PARSED_YET:
        case PARSED_PROGRAM:
            this.zoomIn.setEnabled(false);
            this.zoomOut.setEnabled(false);
            
            if (this.placeholderGraph == null)
                this.buildPlaceholderGraph();
            
            this.setFromGraph(this.placeholderGraph);
            this.viewer.disableNavigation();
            break;
        case PARSED_QUERY:
        case FIRST_STEP:
        case LAST_STEP:
        case NO_MORE_SOLUTIONS:
            this.zoomIn.setEnabled(true);
            this.zoomOut.setEnabled(true);
            this.viewer.enableNavigation();
            break;
        default:
            break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.buildPlaceholderGraph();
        
        if (this.showsPlaceholder)
            this.setFromGraph(this.placeholderGraph);
    }
}
