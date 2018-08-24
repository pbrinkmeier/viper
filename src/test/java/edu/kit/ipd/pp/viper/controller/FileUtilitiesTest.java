package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class FileUtilitiesTest {
    /**
     * Tests the default constructor. This is just to reach 100% coverage,
     * since the class won't ever be constructed.
     */
    @Test
    public void defaultConstructorTest() {
        assertNotNull(new FileUtilities());
    }
    
    /**
     * Tests proper extension adding.
     */
    @Test
    public void extensionAddingTest() {
        File test1 = FileUtilities.checkForMissingExtension(new File("test"), ".test");
        assertTrue(test1.getName().trim().equals("test.test"));
        test1.delete();

        File test2 = FileUtilities.checkForMissingExtension(new File("test.test"), ".test");
        assertTrue(test2.getName().trim().equals("test.test"));
        test2.delete();
    }
}
