package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class UnificationGoalTest {
    private UnificationGoal goal;
    private UnificationGoal uselessGoal;

    /**
     * Initializes the goals "X = Y" and "tuple(X, X) = tuple(X, X)" before each test.
     */
    @Before
    public void init() {
        this.goal
            = new UnificationGoal(new Variable("X"), new Variable("Y"));
        this.uselessGoal
            = new UnificationGoal(
                new Functor("tuple", Arrays.asList(new Variable("X"), new Variable("X"))),
                new Functor("tuple", Arrays.asList(new Variable("X"), new Variable("X")))
            );
    }

    /**
     * Tests the getVariables method, especially that for uselessGoal it only returns the set {X}.
     */
    @Test
    public void getVariablesTest() {
        assertEquals(new HashSet<>(Arrays.asList(new Variable("X"), new Variable("Y"))), this.goal.getVariables());
        assertEquals(new HashSet<>(Arrays.asList(new Variable("X"))), this.uselessGoal.getVariables());
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.goal, null);
        assertNotEquals(this.goal, new Object());
        assertNotEquals(new UnificationGoal(new Variable("X"), new Variable("Z")), this.goal);
        assertNotEquals(new UnificationGoal(new Variable("Y"), new Variable("X")), this.goal);
        assertEquals(new UnificationGoal(new Variable("X"), new Variable("Y")), this.goal);
    }

    /**
     * Tests the transform method using an Indexifier.
     */
    @Test
    public void transformTest() {
        assertEquals(
            new UnificationGoal(new Variable("X", 42), new Variable("Y", 42)),
            this.goal.transform(new Indexifier(42))
        );
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void toStringTest() {
        assertEquals("X = Y", this.goal.toString());
    }

    /**
     * Tests the creation of activation records.
     */
    @Test
    public void createActivationRecordTest() {
        Interpreter interpreter
            = new Interpreter(new KnowledgeBase(Arrays.asList()), this.goal);
        
        assertEquals(this.goal, interpreter.getQuery().getGoal());
    }

    /**
     * Makes sure that the hashCode method is implemented using Objects.hash.
     */
    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash(this.goal.getLhs(), this.goal.getRhs()), this.goal.hashCode());
    }
}
