package edu.kit.ipd.pp.viper.view;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

public class VisualisationPanelTest extends ViewTest {
    private Node rootNode = node("grandfather(X, Y)").with(
            Shape.RECTANGLE,
            ColorScheme.VIS_WHITE,
            ColorScheme.PLACEHOLDER_VIS_COLOR.font());
    
    private Graph testGraph = graph("testGraph")
            .directed()
            .with(rootNode)
            .nodeAttr()
            .with(Font.name("Times New Roman"));
    
    /**
     * Checks if the zoom can be reset accordingly.
     */
    @Ignore
    @Test
    public void resetZoomTest() {
        //this.gui.getVisualisationPanel().setZoomLevel(10);
        this.gui.getVisualisationPanel().resetZoom();
        
        /*assertEquals(VisualisationPanel.DEFAULT_ZOOM_LEVEL,
                this.gui.getVisualisationPanel().getZoomLevel());*/
    }
    
    /**
     * Tests the zoom in functionality.
     */
    @Test
    public void zoomInTest() {
        this.gui.getVisualisationPanel().resetZoom();
        this.gui.getVisualisationPanel().setFromGraph(this.testGraph);
        
        this.gui.getVisualisationPanel().zoom(ZoomType.ZOOM_IN);
        
        /*assertEquals(VisualisationPanel.DEFAULT_ZOOM_LEVEL + 1,
                this.gui.getVisualisationPanel().getZoomLevel());*/
    }
    
    /**
     * Tests the zoom out functionality.
     */
    @Ignore
    @Test
    public void zoomOutTest() {
        this.gui.getVisualisationPanel().resetZoom();
        this.gui.getVisualisationPanel().setFromGraph(this.testGraph);
        
        this.gui.getVisualisationPanel().zoom(ZoomType.ZOOM_OUT);
        
        /*assertEquals(VisualisationPanel.DEFAULT_ZOOM_LEVEL - 1,
                this.gui.getVisualisationPanel().getZoomLevel());*/
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
}
