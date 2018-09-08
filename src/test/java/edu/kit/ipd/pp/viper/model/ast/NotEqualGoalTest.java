package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;

import java.math.BigInteger;

import org.junit.*;
import static org.junit.Assert.*;

public class NotEqualGoalTest {
    private ComparisonGoal goal;

    /**
     * Initializes the goal "X =\= Y" before each test.
     */
    @Before
    public void init() {
        this.goal = new NotEqualGoal(new Variable("X"), new Variable("Y"));
    }

    /**
     * Tests the transform method using an Indexifier.
     */
    @Test
    public void transformTest() {
        assertEquals(
            new NotEqualGoal(new Variable("X", 42), new Variable("Y", 42)),
            this.goal.transform(new Indexifier(42))
        );
    }

    /**
     * Tests the compareNumbers method.
     */
    @Test
    public void compareNumbersTest() {
        assertTrue(this.goal.compareNumbers(new BigInteger("42"), new BigInteger("13")));
        assertFalse(this.goal.compareNumbers(new BigInteger("42"), new BigInteger("42")));
    }
}
