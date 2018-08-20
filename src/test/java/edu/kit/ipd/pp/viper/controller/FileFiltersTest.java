package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.junit.Test;

public class FileFiltersTest {
    /**
     * Tests the default constructor. This is just to reach 100% coverage,
     * since the class won't ever be constructed.
     */
    @Test
    public void testDefaultConstructor() {
        assertNotNull(new FileFilters());
    }
    
    /**
     * Tests the Prolog filter for proper filtering.
     */
    @Test
    public void testPrologFilter() {
        FileFilter filter = FileFilters.PL_FILTER;
        assertTrue(filter.getDescription().equals(LanguageManager.getInstance().getString(LanguageKey.PROLOG_FILES)));

        File directory = new File("test/");
        directory.mkdirs();
        assertTrue(filter.accept(directory));
        assertTrue(filter.accept(new File("test.pl")));
        assertTrue(filter.accept(new File("test.PL")));
        assertTrue(filter.accept(new File("test.pL")));

        assertFalse(filter.accept(new File("test")));
        assertFalse(filter.accept(new File("test.svg")));
        assertFalse(filter.accept(new File("test.tikz")));
        assertFalse(filter.accept(new File("test.png")));
        assertFalse(filter.accept(new File("test.txt")));
        
        directory.delete();
    }

    /**
     * Tests the PNG filter for proper filtering.
     */
    @Test
    public void testPNGFilter() {
        FileFilter filter = FileFilters.PNG_FILTER;
        assertTrue(filter.getDescription().equals(LanguageManager.getInstance().getString(LanguageKey.PNG_FILES)));
       
        File directory = new File("test/");
        directory.mkdirs();
        assertTrue(filter.accept(directory));
        assertTrue(filter.accept(new File("test.png")));
        assertTrue(filter.accept(new File("test.PNG")));
        assertTrue(filter.accept(new File("test.pNg")));

        assertFalse(filter.accept(new File("test")));
        assertFalse(filter.accept(new File("test.svg")));
        assertFalse(filter.accept(new File("test.pl")));
        assertFalse(filter.accept(new File("test.tikz")));

        directory.delete();
    }

    /**
     * Tests the SVG filter for proper filtering.
     */
    @Test
    public void testSVGFilter() {
        FileFilter filter = FileFilters.SVG_FILTER;
        assertTrue(filter.getDescription().equals(LanguageManager.getInstance().getString(LanguageKey.SVG_FILES)));

        File directory = new File("test/");
        directory.mkdirs();
        assertTrue(filter.accept(directory));
        assertTrue(filter.accept(new File("test.svg")));
        assertTrue(filter.accept(new File("test.SVG")));
        assertTrue(filter.accept(new File("test.sVg")));

        assertFalse(filter.accept(new File("test")));
        assertFalse(filter.accept(new File("test.png")));
        assertFalse(filter.accept(new File("test.pl")));
        assertFalse(filter.accept(new File("test.tikz")));
        
        directory.delete();
    }
}
