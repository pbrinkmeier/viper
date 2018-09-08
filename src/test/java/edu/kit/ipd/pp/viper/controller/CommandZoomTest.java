package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.MainWindow;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandZoomTest extends ControllerTest {
    /**
     * Tests the basic functionality of zooming
     * all possible zoom targets.
     * 
     * This test gets ignored as it sometimes fails due to problems
     * in the RSyntaxTextArea library. It works most of the time, though.
     */
    @Ignore
    @Test
    public void zoomingAllTest() {
        CommandZoomTest.buildGraph(this.gui);
        CommandZoomTest.resetAllManually(this.gui);
        
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));

        new CommandZoom(this.gui.getVisualisationPanel(), this.gui.getConsolePanel(),
                this.gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        
        assertTrue(this.gui.getEditorPanel().getFontSize() > PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() > PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getVisualisationPanel().getZoom() > VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        CommandZoomTest.resetAllManually(this.gui);
        
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));
        
        new CommandZoom(this.gui.getVisualisationPanel(), this.gui.getConsolePanel(),
                this.gui.getEditorPanel(), ZoomType.ZOOM_OUT).execute();
        
        assertTrue(this.gui.getEditorPanel().getFontSize() < PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() < PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getVisualisationPanel().getZoom() < VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    /**
     * Tests whether passing null for the console properly
     * ignores it, but still zooms everything else.
     * 
     * This test gets ignored as it sometimes fails due to problems
     * in the RSyntaxTextArea library. It works most of the time, though.
     */
    @Ignore
    @Test
    public void nullConsoleTest() {
        CommandZoomTest.buildGraph(this.gui);
        CommandZoomTest.resetAllManually(this.gui);
        
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));

        new CommandZoom(this.gui.getVisualisationPanel(), null, this.gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        assertTrue(this.gui.getEditorPanel().getFontSize() > PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getVisualisationPanel().getZoom() > VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        CommandZoomTest.resetAllManually(this.gui);

        new CommandZoom(this.gui.getVisualisationPanel(), null, this.gui.getEditorPanel(), ZoomType.ZOOM_OUT).execute();
        assertTrue(this.gui.getEditorPanel().getFontSize() < PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getVisualisationPanel().getZoom() < VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    /**
     * Tests whether passing null for the editor properly
     * ignores it, but still zooms everything else.
     */
    @Test
    public void nullEditorTest() {
        CommandZoomTest.buildGraph(this.gui);
        CommandZoomTest.resetAllManually(this.gui);
        
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));

        new CommandZoom(this.gui.getVisualisationPanel(), this.gui.getConsolePanel(), null, ZoomType.ZOOM_IN).execute();
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() > PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getVisualisationPanel().getZoom() > VisualisationPanel.DEFAULT_ZOOM_LEVEL);
        
        CommandZoomTest.resetAllManually(this.gui);

        new CommandZoom(this.gui.getVisualisationPanel(), this.gui.getConsolePanel(), null,
                ZoomType.ZOOM_OUT).execute();
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() < PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getVisualisationPanel().getZoom() < VisualisationPanel.DEFAULT_ZOOM_LEVEL);
    }
    
    /**
     * Tests whether passing null for the visualisation properly
     * ignores it, but still zooms everything else.
     * 
     * This test gets ignored as it sometimes fails due to problems
     * in the RSyntaxTextArea library. It works most of the time, though.
     */
    @Ignore
    @Test
    public void nullVisualisationTest() {
        CommandZoomTest.resetAllManually(this.gui);
        
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));

        new CommandZoom(null, this.gui.getConsolePanel(), this.gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        assertTrue(this.gui.getEditorPanel().getFontSize() > PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() > PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));
        
        CommandZoomTest.resetAllManually(this.gui);
        
        new CommandZoom(null, this.gui.getConsolePanel(), this.gui.getEditorPanel(), ZoomType.ZOOM_OUT).execute();
        assertTrue(this.gui.getEditorPanel().getFontSize() < PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() < PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));
    }
    
    /**
     * Tests whether resetting the zoom of everything via CommandResetZoom
     * works properly.
     * 
     * This test gets ignored as it sometimes fails due to problems
     * in the RSyntaxTextArea library. It works most of the time, though.
     */
    @Ignore
    @Test
    public void commandResetZoomTest() {
        CommandZoomTest.buildGraph(this.gui);
        CommandZoomTest.resetAllManually(this.gui);
        
        new CommandZoom(this.gui.getVisualisationPanel(), this.gui.getConsolePanel(),
                this.gui.getEditorPanel(), ZoomType.ZOOM_IN).execute();
        
        assertFalse(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertFalse(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertFalse(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));
        
        new CommandResetZoom(this.gui.getVisualisationPanel(), this.gui.getConsolePanel(),
                this.gui.getEditorPanel()).execute();
        
        assertTrue(this.gui.getEditorPanel().getFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(this.gui.getConsolePanel().getOutputAreaFontSize() == PreferencesManager.DEFAULT_TEXT_SIZE);
        assertTrue(CommandZoomTest.epsilonEquals(this.gui.getVisualisationPanel().getZoom(),
                VisualisationPanel.DEFAULT_ZOOM_LEVEL));
    }
    
    private static void buildGraph(MainWindow gui) {
        gui.getCommandParse().execute();
        gui.getConsolePanel().setInputFieldText("test(X).");
        new CommandParseQuery(gui.getConsolePanel(), gui.getVisualisationPanel(),
                gui.getInterpreterManager(), gui::switchClickableState).execute();
    }
    
    private static void resetAllManually(MainWindow gui) {
        gui.getEditorPanel().resetZoom();
        gui.getConsolePanel().resetZoom();
        gui.getVisualisationPanel().resetZoom();
    }
    
    private static boolean epsilonEquals(double a, double b) {
        final double epsilon = 0.001;
        return Math.abs(a - b) < epsilon;
    }
}
