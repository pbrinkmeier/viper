package edu.kit.ipd.pp.viper.view;

import javax.swing.JPanel;

import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.model.Graph;

/**
 * Represents a panel containing a SVG viewer
 */
public class VisualisationPanel extends JPanel {
    /**
     * Viewer to use
     */
    private final VisualisationViewer viewer;

    /**
     * Creates a new viewer panel
     */
    public VisualisationPanel() {
        super();

        this.viewer = new VisualisationViewer();
    }

    /**
     * Clears the currently shown visualisation
     */
    public void clearVisualization() {
        this.viewer.clear();
    }

    /**
     * Performs a zoom operation on the currently shown graph
     * 
     * @param direction Direction to zoom (in or out)
     */
    public void zoom(ZoomType direction) {
    }

    /**
     * Sets the displayed Graph. A previously shown graph will be cleared.
     * 
     * @param graph The graph to show
     */
    public void setFromGraph(Graph graph) {
        this.clearVisualization();
        this.viewer.setFromGraph(graph);
    }
}
