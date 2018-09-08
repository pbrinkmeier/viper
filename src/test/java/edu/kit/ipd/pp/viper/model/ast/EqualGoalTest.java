package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;

import java.math.BigInteger;

import org.junit.*;
import static org.junit.Assert.*;

public class EqualGoalTest {
    private ComparisonGoal goal;

    /**
     * Initializes the goal "X =:= Y".
     */
    @Before
    public void init() {
        this.goal = new EqualGoal(new Variable("X"), new Variable("Y"));
    }

    /**
     * Tests the transform method using an Indexifier.
     */
    @Test
    public void transformTest() {
        assertEquals(
            new EqualGoal(new Variable("X", 42), new Variable("Y", 42)),
            this.goal.transform(new Indexifier(42))
        );
    }

    /**
     * Makes sure that the comparison of numbers works.
     */
    @Test
    public void compareNumbersTest() {
        assertTrue(this.goal.compareNumbers(new BigInteger("42"), new BigInteger("42")));
        assertFalse(this.goal.compareNumbers(new BigInteger("42"), new BigInteger("13")));
    }
}
