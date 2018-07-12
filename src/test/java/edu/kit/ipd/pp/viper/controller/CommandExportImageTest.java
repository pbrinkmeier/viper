package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.junit.Test;

public class CommandExportImageTest {
    /**
     * Tests the PNG filter for proper filtering.
     */
    @Test
    public void testPNGFilter() {
        FileFilter filter = CommandExportImage.PNG_FILTER;
        assertTrue(filter.accept(new File("test.png")));
        assertTrue(filter.accept(new File("test.PNG")));
        assertTrue(filter.accept(new File("test.pNg")));
        
        assertFalse(filter.accept(new File("test")));
        assertFalse(filter.accept(new File("test.svg")));
        assertFalse(filter.accept(new File("test.pl")));
        assertFalse(filter.accept(new File("test.tikz")));
    }

    /**
     * Tests the SVG filter for proper filtering.
     */
    @Test
    public void testSVGFilter() {
        FileFilter filter = CommandExportImage.SVG_FILTER;   
        assertTrue(filter.accept(new File("test.svg")));
        assertTrue(filter.accept(new File("test.SVG")));
        assertTrue(filter.accept(new File("test.sVg")));
        
        assertFalse(filter.accept(new File("test")));
        assertFalse(filter.accept(new File("test.png")));
        assertFalse(filter.accept(new File("test.pl")));
        assertFalse(filter.accept(new File("test.tikz")));
    }
}
