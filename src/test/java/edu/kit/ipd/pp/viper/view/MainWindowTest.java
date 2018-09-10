package edu.kit.ipd.pp.viper.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainWindowTest extends ViewTest {
    
    /**
     * Tests the single parameter constructor for the MainWindow class
     */
    @Test
    public void singleParameterConstructorTest() {
        MainWindow testGui = new MainWindow(true);

        assertEquals(true, MainWindow.inDebugMode());

        testGui.dispose();
        testGui = new MainWindow(false);

        assertEquals(false, MainWindow.inDebugMode());
        
        testGui.dispose();
    }    

    /**
     * Tests the setWindowTitle function when changing the title to something else
     */
    @Test
    public void setWindowTitleTest() {
        this.gui.setWindowTitle("Testtitle");

        assertEquals("Testtitle", this.gui.getWindowTitle());
    }

    /**
     * Tests the setWindowTitle function when setting the title to an empty string, which
     * resets it to the default title
     */
    @Test
    public void setEmptyWindowTitleTest() {
        this.gui.setWindowTitle("placeholder");
        this.gui.setWindowTitle("");

        assertEquals("", this.gui.getWindowTitle());
    }
    
    /**
     * Tests if an asterisk is appended to the title if the title is not empty
     */
    public void setAppendAsterixToTitleTest() {
        this.gui.setWindowTitle("");
        
        this.gui.setAppendAsterixToTitle(true);
        
        assertEquals("", this.gui.getWindowTitle());
        
        this.gui.setWindowTitle("Testtitle");
        
        this.gui.setAppendAsterixToTitle(true);
        
        assertEquals("Testtitle*", this.gui.getWindowTitle());
    }
    
    /**
     * Only calls the main function to check that no exceptions are thrown
     */
    @Test
    public void mainTest() {
        String[] args = {""};
        MainWindow.main(args);
    }
}
