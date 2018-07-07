package edu.kit.ipd.pp.viper.view;

import org.apache.batik.swing.JSVGCanvas;

import guru.nidi.graphviz.model.Graph;

/**
 * Represents an interactive SVG viewer, customized for the needs of this program.
 */
public class VisualisationViewer extends JSVGCanvas {
    /**
     * Creates a new interactive viewer
     */
    public VisualisationViewer() {
        super();

        this.setEnableRotateInteractor(false);
        this.setEnablePanInteractor(false);
    }

    /**
     * Clears the displayed SVG
     */
    public void clear() {
    }

    /**
     * Sets the displayed Graph. A previously shown graph will be cleared.
     * 
     * @param graph The graph to show
     */
    public void setFromGraph(Graph graph) {
    }
}
