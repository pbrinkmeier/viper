package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.junit.Test;

public class CommandExportTikzTest {
    /**
     * Tests the TikZ filter for proper filtering.
     */
    @Test
    public void testTikZFilter() {
        FileFilter filter = CommandExportTikz.TIKZ_FILTER;
        assertTrue(filter.accept(new File("test.tikz")));
        assertTrue(filter.accept(new File("test.TIKZ")));
        assertTrue(filter.accept(new File("test.tiKz")));
        
        assertFalse(filter.accept(new File("test")));
        assertFalse(filter.accept(new File("test.svg")));
        assertFalse(filter.accept(new File("test.pl")));
        assertFalse(filter.accept(new File("test.png")));
        assertFalse(filter.accept(new File("test.txt")));
    }
}
