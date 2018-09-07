package edu.kit.ipd.pp.viper.view;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

public class VisualisationPanelTest {
    private MainWindow gui;
    private VisualisationPanel visualisation;
    
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
     * Constructs the GUI.
     */
    @Before
    public void buildGUI() {
        this.gui = new MainWindow(true);
        this.gui.setVisible(false);
        this.visualisation = this.gui.getVisualisationPanel();
    }
    
    /**
     * Checks if the zoom can be reset accordingly.
     */
    @Test
    public void resetZoomTest() {
        this.visualisation.setZoomLevel(10);
        this.visualisation.resetZoom();
        
        assertEquals(VisualisationPanel.DEFAULT_ZOOM_LEVEL,
                this.visualisation.getZoomLevel());
    }
    
    /**
     * Tests the zoom in functionality.
     */
    @Test
    public void zoomInTest() {
        this.visualisation.resetZoom();
        this.visualisation.setFromGraph(this.testGraph);
        
        this.visualisation.zoom(ZoomType.ZOOM_IN);
        
        assertEquals(VisualisationPanel.DEFAULT_ZOOM_LEVEL + 1,
                this.visualisation.getZoomLevel());
    }
    
    /**
     * Tests the zoom out functionality.
     */
    @Test
    public void zoomOutTest() {
        this.visualisation.resetZoom();
        this.visualisation.setFromGraph(this.testGraph);
        
        this.visualisation.zoom(ZoomType.ZOOM_OUT);
        
        assertEquals(VisualisationPanel.DEFAULT_ZOOM_LEVEL - 1,
                this.visualisation.getZoomLevel());
    }
    
    /**
     * Tests the return value if the visualisation panel doesn't have a graph.
     * Simultaneously tests the return value if the visualisation panel shows the placeholder.
     */
    @Test
    public void hasGraphFalseTest() {
        this.visualisation.clearVisualization();
        
        assertEquals(false, this.visualisation.hasGraph());
        assertEquals(true, this.visualisation.showsPlaceholder());
    }
    
    /**
     * Tests the return value if the visualisation panel has a graph.
     * Simultaneously tests the return value if the visualisation panel doesn't show the placeholder.
     */
    @Test
    public void hasGraphTrueTest() {
        this.visualisation.setFromGraph(this.testGraph);
        
        assertEquals(true, this.visualisation.hasGraph());
        assertEquals(false, this.visualisation.showsPlaceholder());
    }
}
