package edu.kit.ipd.pp.viper.view;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

import java.awt.event.ActionEvent;

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
            .with(rootNode)
            .nodeAttr()
            .with(Font.name("Times New Roman"));
    
    /**
     * Tests if the zoom level of the visualisation viewer is reset accordingly.
     */
    @Test
    public void resetZoomTest() {
        
    }
    
    /**
     * Tests if the zoom in functionality works accordingly.
     */
    @Test
    public void zoomInTest() {
        
    }
    
    /**
     * Tests if the zoom out functionality works accordingly.
     */
    @Test
    public void zoomOutTest() {
        
    }
    
    /**
     * Tests if graphs are set correctly in the viewer.
     */
    @Test
    public void setFromGraphTest() {
        
    }
    
    /**
     * Tests if zooming in via the mousewheel works
     */
    @Test
    public void mouseWheelMovedZoomInTest() {
        
    }
    
    /**
     * Tests if zooming out via the mousewheel works
     */
    @Test
    public void mouseWheelMovedZoomOutTest() {
        
    }
}
