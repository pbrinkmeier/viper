package edu.kit.ipd.pp.viper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import edu.kit.ipd.pp.viper.controller.CommandNextStep;
import edu.kit.ipd.pp.viper.controller.CommandZoom;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.model.Graph;

/**
 * Represents a panel containing a SVG viewer
 */
public class VisualisationPanel extends JPanel implements ComponentListener, HasClickable {
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
    private Button buttonStep;

    private boolean hasGraph;

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
        this.setBackground(Color.WHITE);

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
        contentPane.add(zoomIn, new Integer(2));
        contentPane.add(zoomOut, new Integer(2));

        // panel for the buttons below the visualisation
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.buttonStep = new Button(LanguageKey.BUTTON_STEP, new CommandNextStep(this.main.getVisualisationPanel(),
                this.main.getInterpreterManager(), this.main.getConsolePanel()));
        buttonPanel.add(buttonStep);

        this.add(contentPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.hasGraph = false;
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
        this.clearVisualization();
        this.viewer.setFromGraph(graph);
        this.hasGraph = true;
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
            this.buttonStep.setEnabled(false);
            break;
        case PARSED_QUERY:
            this.zoomIn.setEnabled(true);
            this.zoomOut.setEnabled(true);
            this.buttonStep.setEnabled(true);
            break;
        default:
            break;
        }
    }
}
