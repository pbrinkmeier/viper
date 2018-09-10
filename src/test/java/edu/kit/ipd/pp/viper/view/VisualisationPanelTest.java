package edu.kit.ipd.pp.viper.view;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

/**
 * All the zoom tests in this class only check if there are no exceptions when
 * calling the respective methods of the visualisation panel, because the panel
 * only calls the respective methods in the viewer and thus you'd test those
 * functionalities twice.
 */
public class VisualisationPanelTest extends ViewTest {
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
     * Resets the zoom to test if any exceptions are thrown.
     */
    @Test
    public void resetZoomTest() {
        this.gui.getVisualisationPanel().resetZoom();
    }
    
    /**
     * Tests the zoom function if the placeholder graph is still shown.
     */
    @Test
    public void zoomPlaceholderTest() {
        this.gui.getVisualisationPanel().clearVisualization();
        this.gui.getVisualisationPanel().resetZoom();
        
        this.gui.getVisualisationPanel().zoom(ZoomType.ZOOM_IN);
    }
    
    /**
     * Tests the zoom in functionality.
     */
    @Test
    public void zoomInTest() {
        this.gui.getVisualisationPanel().resetZoom();
        this.gui.getVisualisationPanel().setFromGraph(this.testGraph);
        
        this.gui.getVisualisationPanel().zoom(ZoomType.ZOOM_IN);
    }
    
    /**
     * Tests the zoom out functionality.
     */
    @Test
    public void zoomOutTest() {
        this.gui.getVisualisationPanel().resetZoom();
        this.gui.getVisualisationPanel().setFromGraph(this.testGraph);
        
        this.gui.getVisualisationPanel().zoom(ZoomType.ZOOM_OUT);
    }
    
    /**
     * Tests the return value if the visualisation panel doesn't have a graph.
     * Simultaneously tests the return value if the visualisation panel shows the placeholder.
     */
    @Test
    public void hasGraphFalseTest() {
        this.gui.getVisualisationPanel().clearVisualization();
        
        assertEquals(false, this.gui.getVisualisationPanel().hasGraph());
        assertEquals(true, this.gui.getVisualisationPanel().showsPlaceholder());
    }
    
    /**
     * Tests the return value if the visualisation panel has a graph.
     * Simultaneously tests the return value if the visualisation panel doesn't show the placeholder.
     */
    @Test
    public void hasGraphTrueTest() {
        this.gui.getVisualisationPanel().setFromGraph(this.testGraph);
        
        assertEquals(true, this.gui.getVisualisationPanel().hasGraph());
        assertEquals(false, this.gui.getVisualisationPanel().showsPlaceholder());
    }
    
    /**
     * Tests if the graph isn't updated if the graph in the function call is null.
     */
    @Test
    public void setFromGraphNullTest() {
        this.gui.getVisualisationPanel().clearVisualization();
        this.gui.getVisualisationPanel().setFromGraph(this.testGraph);
        
        this.gui.getVisualisationPanel().setFromGraph(null);
        
        assertEquals(this.testGraph,
                this.gui.getVisualisationPanel().getVisualisationViewer().getGraph());
    }
    
    /**
     * Tests the switchClickableState functino with the parsed program state and a placeholder graph that's null.
     */
    @Test
    public void switchClickableStateNoPlaceholderTest() {
        this.gui.getVisualisationPanel().clearVisualization();
        this.gui.getVisualisationPanel().clearPlaceholder();
        
        assertEquals(null, this.gui.getVisualisationPanel().getPlaceholder());
        
        this.gui.getVisualisationPanel().switchClickableState(ClickableState.PARSED_PROGRAM);
        
        assertTrue(this.gui.getVisualisationPanel().getPlaceholder() != null);
    }
    
    /**
     * Calls the componentHidden() function. Since this function doesn't do anything this is only for coverage.
     */
    @Test
    public void componentHiddenTest() {
        this.gui.getVisualisationPanel().componentHidden(null);
    }
    
    /**
     * Calls the componentMoved() function. Since this function doesn't do anything this is only for coverage.
     */
    @Test
    public void componentMovedTest() {
        this.gui.getVisualisationPanel().componentMoved(null);
    }
    
    /**
     * Calls the componentShown() function. Since this function doesn't do anything this is only for coverage.
     */
    @Test
    public void componentShownTest() {
        this.gui.getVisualisationPanel().componentShown(null);
    }
}
