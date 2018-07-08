package edu.kit.ipd.pp.viper.view;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.Action;
import javax.swing.ActionMap;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.AbstractPanInteractor;
import org.apache.batik.swing.gvt.Interactor;

import guru.nidi.graphviz.model.Graph;

/**
 * Represents an interactive SVG viewer, customized for the needs of this program.
 */
public class VisualisationViewer extends JSVGCanvas implements MouseWheelListener {
    /**
     * Creates a new interactive viewer
     */
    public VisualisationViewer() {
        super();

        // disable default rotation and panning iteractors
        this.setEnableRotateInteractor(false);
        this.setEnablePanInteractor(false);

        // add new iteractor that moves the image when left-clicking and holding the mouse
        Interactor panInteractor = new AbstractPanInteractor() {
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
     * @param event 
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        double rot = event.getPreciseWheelRotation();
        ActionMap map = this.getActionMap();
        Action action = null;

        if (event.isAltDown()) {
            if (rot > 0.0)
                action = map.get(JSVGCanvas.SCROLL_LEFT_ACTION);
            else
                action = map.get(JSVGCanvas.SCROLL_RIGHT_ACTION);
        } else {
            if (rot > 0.0)
                action = map.get(JSVGCanvas.ZOOM_OUT_ACTION);
            else
                action = map.get(JSVGCanvas.ZOOM_IN_ACTION);
        }

        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
        String path = this.getClass().getResource("/graph_placeholder.svg").getPath();
        this.setURI(path);
    }
}
