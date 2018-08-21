package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.viper.view.ConsoleOutputArea;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandZoomTest {
    /**
     * Tests the basic functionality of zooming
     * all possible zoom targets.
     */
    @Test
    public void testZoomingAll() {
        MainWindow gui = new MainWindow(false);
        
        this.buildGraph(gui);
        this.resetAllManually(gui);
        
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        new CommandZoom(gui.getVisualisationPanel(), gui.getConsolePanel(),
                gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        
        assertTrue(gui.getEditorPanel().getFontSize() > EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() > ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() > VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        this.resetAllManually(gui);
        
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        new CommandZoom(gui.getVisualisationPanel(), gui.getConsolePanel(),
                gui.getEditorPanel(), ZoomType.ZOOM_OUT).execute();
        
        assertTrue(gui.getEditorPanel().getFontSize() < EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() < ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() < VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    /**
     * Tests whether passing null for the console properly
     * ignores it, but still zooms everything else.
     */
    @Test
    public void testNullConsole() {
        MainWindow gui = new MainWindow(false);

        this.buildGraph(gui);
        this.resetAllManually(gui);
        
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);

        new CommandZoom(gui.getVisualisationPanel(), null, gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        assertTrue(gui.getEditorPanel().getFontSize() > EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() > VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        this.resetAllManually(gui);

        new CommandZoom(gui.getVisualisationPanel(), null, gui.getEditorPanel(), ZoomType.ZOOM_OUT).execute();
        assertTrue(gui.getEditorPanel().getFontSize() < EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() < VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    /**
     * Tests whether passing null for the editor properly
     * ignores it, but still zooms everything else.
     */
    @Test
    public void testNullEditor() {
        MainWindow gui = new MainWindow(false);

        this.buildGraph(gui);
        this.resetAllManually(gui);
        
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);

        new CommandZoom(gui.getVisualisationPanel(), gui.getConsolePanel(), null, ZoomType.ZOOM_IN).execute();
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() > ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() > VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        this.resetAllManually(gui);

        new CommandZoom(gui.getVisualisationPanel(), gui.getConsolePanel(), null, ZoomType.ZOOM_OUT).execute();
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() < ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() < VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    /**
     * Tests whether passing null for the visualisation properly
     * ignores it, but still zooms everything else.
     */
    @Test
    public void testNullVisualisation() {
        MainWindow gui = new MainWindow(false);

        this.resetAllManually(gui);
        
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);

        new CommandZoom(null, gui.getConsolePanel(), gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        assertTrue(gui.getEditorPanel().getFontSize() > EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() > ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        this.resetAllManually(gui);
        
        new CommandZoom(null, gui.getConsolePanel(), gui.getEditorPanel(), ZoomType.ZOOM_OUT).execute();
        assertTrue(gui.getEditorPanel().getFontSize() < EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() < ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    /**
     * Tests whether resetting the zoom of everything via CommandResetZoom
     * works properly.
     */
    @Test
    public void testCommandResetZoom() {
        MainWindow gui = new MainWindow(false);

        this.buildGraph(gui);
        this.resetAllManually(gui);
        
        new CommandZoom(gui.getVisualisationPanel(), gui.getConsolePanel(),
                gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        
        assertFalse(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertFalse(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertFalse(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        new CommandResetZoom(gui.getVisualisationPanel(), gui.getConsolePanel(),
                gui.getEditorPanel()).execute();
        
        assertTrue(gui.getEditorPanel().getFontSize() == EditorPanel.FONT_DEFAULT_SIZE);
        assertTrue(gui.getConsolePanel().getOutputAreaFontSize() == ConsoleOutputArea.FONT_DEFAULT_SIZE);
        assertTrue(gui.getVisualisationPanel().getZoomLevel() == VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    private void buildGraph(MainWindow gui) {
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui::switchClickableState).execute();
    }
    
    private void resetAllManually(MainWindow gui) {
        gui.getEditorPanel().resetZoom();
        gui.getConsolePanel().resetZoom();
        gui.getVisualisationPanel().resetZoom();
    }
}
