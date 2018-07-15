package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

// These tests rely heavily on the Number class; all tests for Number should be fulfilled first
public class MultiplicationOperationTest {
    private MultiplicationOperation fortyTwo;
    private MultiplicationOperation notFortyTwo;

    /**
     * Initializes the multiplication operations used in the tests.
     */
    @Before
    public void init() {
        this.fortyTwo = new MultiplicationOperation(new Number(6), new Number(7));
        this.notFortyTwo = new MultiplicationOperation(new Number(6), new Number(8));
    }

    /**
     * Tests the proper calculation of the multiplication operations.
     * 
     * @throws TermEvaluationException if the operations couldn't be evaluated
     */
    @Test
    public void calculateTest() throws TermEvaluationException {
        assertEquals(fortyTwo.evaluate(), new Number(42));
        assertNotEquals(notFortyTwo.evaluate(), new Number(42));
        assertEquals(notFortyTwo.evaluate(), new Number(48));
    }

    /**
     * Tests the creation of a new multiplication operation from an
     * already instantiated multiplication operation object.
     */
    @Test
    public void createNewTest() {
        assertEquals(this.fortyTwo.createNew(Arrays.asList(new Number(1), new Number(2))),
                new MultiplicationOperation(new Number(1), new Number(2)));
    }
}
