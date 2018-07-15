package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

// These tests rely heavily on the Number class; all tests for Number should be fulfilled first
public class AdditionOperationTest {
    private AdditionOperation fortyTwo;
    private AdditionOperation notFortyTwo;

    /**
     * Initializes the addition operations used in the tests.
     */
    @Before
    public void init() {
        this.fortyTwo = new AdditionOperation(new Number(17), new Number(25));
        this.notFortyTwo = new AdditionOperation(new Number(17), new Number(24));
    }

    /**
     * Tests basic addition calculations.
     * 
     * @throws TermEvaluationException if the evaluation fails
     */
    @Test
    public void calculateTest() throws TermEvaluationException {
        assertEquals(fortyTwo.evaluate(), new Number(42));
        assertNotEquals(notFortyTwo.evaluate(), new Number(42));
        assertEquals(notFortyTwo.evaluate(), new Number(41));
    }

    /**
     * Tests the creation of a new addition operation from an already
     * instantiated addition operation object.
     */
    @Test
    public void createNewTest() {
        assertEquals(this.fortyTwo.createNew(Arrays.asList(new Number(1), new Number(2))),
                new AdditionOperation(new Number(1), new Number(2)));
    }
}
