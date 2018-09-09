package edu.kit.ipd.pp.viper.view;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static org.junit.Assert.assertEquals;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.apache.batik.swing.gvt.AbstractPanInteractor;
import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

public class VisualisationViewerTest extends ViewTest {
    private Node rootNode = node("grandfather(X, Y)").with(
            Shape.RECTANGLE,
            ColorScheme.VIS_WHITE,
            ColorScheme.PLACEHOLDER_VIS_COLOR.font());
    
    private Graph testGraph = graph("testGraph")
            .directed()
            .with(this.rootNode)
            .nodeAttr()
            .with(Font.name("Times New Roman"));
    
    /**
     * Tests if the zoom level of the visualisation viewer is reset accordingly.
     */
    @Test
    public void resetZoomTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setScale(3.0);
        this.gui.getVisualisationPanel().getVisualisationViewer().resetZoom();
        
        assertEquals(VisualisationViewer.DEFAULT_ZOOM,
                this.gui.getVisualisationPanel().getVisualisationViewer().getScale(), 0.0);
    }
    
    /**
     * Tests if the zoom in functionality works accordingly.
     */
    @Test
    public void zoomInTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setFromGraph(this.testGraph);
        this.gui.getVisualisationPanel().getVisualisationViewer().resetZoom();
            
        for (int i = 0; i < 5; i++) {
            this.gui.getVisualisationPanel().getVisualisationViewer().zoom(ZoomType.ZOOM_IN);
        }
            
        /* The VisualisationViewer doesn't zoom with fixed values but instead scales the image with
         * a value that's very close to 0.01, so there will be slight differences between zooming in
         * fives times and adding 0.05 to the default value
         */
        assertEquals(VisualisationViewer.DEFAULT_ZOOM + 0.05,
                this.gui.getVisualisationPanel().getVisualisationViewer().getScale(), 0.01);
    }
    
    /**
     * Tests if the zoom out functionality works accordingly.
     */
    @Test
    public void zoomOutTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setFromGraph(this.testGraph);
        this.gui.getVisualisationPanel().getVisualisationViewer().resetZoom();
        
        for (int i = 0; i < 5; i++) {
            this.gui.getVisualisationPanel().getVisualisationViewer().zoom(ZoomType.ZOOM_OUT);
        }
        
        /* The VisualisationViewer doesn't zoom with fixed values but instead scales the image with
         * a value that's very close to -0.1, so there will be slight differences between zooming out
         * fives times and subtracting 0.5 from the default value
         */
        assertEquals(VisualisationViewer.DEFAULT_ZOOM - 0.5,
                this.gui.getVisualisationPanel().getVisualisationViewer().getScale(), 0.01);
    }
    
    /**
     * Tests if graphs are set correctly in the viewer.
     */
    @Test
    public void setFromGraphTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setFromGraph(this.testGraph);
        
        assertEquals(this.testGraph,
                this.gui.getVisualisationPanel().getVisualisationViewer().getGraph());
    }
    
    /**
     * Tests if zooming in via the mousewheel works
     */
    @Test
    public void mouseWheelMovedZoomInTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setFromGraph(this.testGraph);
        this.gui.getVisualisationPanel().getVisualisationViewer().resetZoom();
        this.gui.getVisualisationPanel().getVisualisationViewer().enableNavigation();
        
        for (int i = 0; i < 5; i++) {
            this.gui.getVisualisationPanel().getVisualisationViewer()
                .mouseWheelMoved(new MouseWheelEvent(this.gui.getVisualisationPanel().getVisualisationViewer(),
                    1, 1, 0, 100, 100, 1, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, -1));
        }
        
        /* The VisualisationViewer doesn't zoom with fixed values but instead scales the image with
         * a value that's very close to 0.06, so there will be slight differences between the scale value after
         * zooming in fives times and adding 0.3 to the default value
         */
        assertEquals(VisualisationViewer.DEFAULT_ZOOM + 0.3,
                this.gui.getVisualisationPanel().getVisualisationViewer().getScale(), 0.02);
    }
    
    /**
     * Tests if zooming out via the mousewheel works
     */
    @Test
    public void mouseWheelMovedZoomOutTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setFromGraph(this.testGraph);
        this.gui.getVisualisationPanel().getVisualisationViewer().resetZoom();
        this.gui.getVisualisationPanel().getVisualisationViewer().enableNavigation();
        
        for (int i = 0; i < 5; i++) {
            this.gui.getVisualisationPanel().getVisualisationViewer()
                .mouseWheelMoved(new MouseWheelEvent(this.gui.getVisualisationPanel().getVisualisationViewer(),
                    1, 1, 0, 100, 100, 1, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, 1));
        }
        
        /* The VisualisationViewer doesn't zoom with fixed values but instead scales the image with
         * a value that's very close to -0.045, so there will be slight differences between the scale value
         * after zooming out fives times and subtracting 0.225 from the default value
         */
        assertEquals(VisualisationViewer.DEFAULT_ZOOM - 0.225,
                this.gui.getVisualisationPanel().getVisualisationViewer().getScale(), 0.02);
    }
    
    /**
     * Tests if the viewer doesn't zoom when the navigation is disabled
     */
    @Test
    public void mouseWheelMovedNoNavigationTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setFromGraph(this.testGraph);
        this.gui.getVisualisationPanel().getVisualisationViewer().resetZoom();
        this.gui.getVisualisationPanel().getVisualisationViewer().disableNavigation();
        
        for (int i = 0; i < 5; i++) {
            this.gui.getVisualisationPanel().getVisualisationViewer()
                .mouseWheelMoved(new MouseWheelEvent(this.gui.getVisualisationPanel().getVisualisationViewer(),
                    1, 1, 0, 100, 100, 1, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, 1));
        }
        
        /* The VisualisationViewer doesn't zoom with fixed values but instead scales the image with
         * a value that's very close to -0.045, so there will be slight differences between the scale value
         * after zooming out fives times and subtracting 0.225 from the default value
         */
        assertEquals(VisualisationViewer.DEFAULT_ZOOM,
                this.gui.getVisualisationPanel().getVisualisationViewer().getScale(), 0.0);
    }
    
    /**
     * Calls the setCursor method once. Since this method does nothing this test is only for coverage.
     */
    @Test
    public void setCursorTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().setCursor(null);
    }
    
    /**
     * Clears the visualisation. Only checks if any exceptions are thrown.
     */
    @Test
    public void clearTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().clear();
    }
    
    /**
     * Only calls this function to check that no exceptions occur,
     * since assertions if the current view has been moved are not possible.
     */
    public void panInteractorTest() {
        this.gui.getVisualisationPanel().getVisualisationViewer().enableNavigation();
        
        ((AbstractPanInteractor) this.gui.getVisualisationPanel().getVisualisationViewer().getInteractors().get(
                this.gui.getVisualisationPanel().getVisualisationViewer().getInteractors().size() - 1))
                    .startInteraction(new MouseEvent(this.gui.getVisualisationPanel().getVisualisationViewer(),
                            1, 1, 1, 1, 1, 1, false, MouseEvent.BUTTON1));
    }
}
