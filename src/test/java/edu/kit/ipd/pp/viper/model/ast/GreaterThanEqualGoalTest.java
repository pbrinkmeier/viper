package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;

import java.math.BigInteger;

import org.junit.*;
import static org.junit.Assert.*;

public class GreaterThanEqualGoalTest {
    private ComparisonGoal goal;

    @Before
    public void init() {
        this.goal = new GreaterThanEqualGoal(new Variable("X"), new Variable("Y"));
    }

    @Test
    public void transformTest() {
        assertEquals(
            new GreaterThanEqualGoal(new Variable("X", 42), new Variable("Y", 42)),
            this.goal.transform(new Indexifier(42))
        );
    }

    @Test
    public void compareNumbersTest() {
        assertTrue(this.goal.compareNumbers(new BigInteger("42"), new BigInteger("13")));
        assertTrue(this.goal.compareNumbers(new BigInteger("42"), new BigInteger("42")));
        assertFalse(this.goal.compareNumbers(new BigInteger("13"), new BigInteger("42")));
    }
}
