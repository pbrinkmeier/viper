package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

// These tests rely heavily on the Number class; all tests for Number should be fulfilled first
public class SubtractionOperationTest {
    private SubtractionOperation fortyTwo;
    private SubtractionOperation notFortyTwo;

    /**
     * Initializes the subtraction operations used in the tests.
     */
    @Before
    public void init() {
        this.fortyTwo = new SubtractionOperation(new Number(67), new Number(25));
        this.notFortyTwo = new SubtractionOperation(new Number(67), new Number(26));
    }

    /**
     * Tests the proper calculation of the subtraction operations.
     * 
     * @throws TermEvaluationException if the calculation couldn't be evaluated
     */
    @Test
    public void calculateTest() throws TermEvaluationException {
        assertEquals(fortyTwo.evaluate(), new Number(42));
        assertNotEquals(notFortyTwo.evaluate(), new Number(42));
        assertEquals(notFortyTwo.evaluate(), new Number(41));
    }

    /**
     * Tests the creation of a new subtraction operation from an
     * already instantiated subtraction operation object.
     */
    @Test
    public void createNewTest() {
        assertEquals(this.fortyTwo.createNew(Arrays.asList(new Number(1), new Number(2))),
                new SubtractionOperation(new Number(1), new Number(2)));
    }
}
