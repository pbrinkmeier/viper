package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class CutGoalTest {
    private Goal goal;

    /**
     * Initializes a cut goal before each test.
     */
    @Before
    public void init() {
        this.goal = new CutGoal();
    }

    /**
     * Two cut goals are always equal.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.goal, null);
        assertNotEquals(this.goal, new Object());
        assertEquals(this.goal, new CutGoal());
    }
    
    /**
     * Ensures that the hashCode method is implemented using the Objects.hash helper method.
     */
    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash("!"), this.goal.hashCode());
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void toStringTest() {
        assertEquals("!", this.goal.toString());
    }

    /**
     * Tests the transform method.
     * Since two CutGoals are always the same, transformations shouldn't have any effect.
     */
    @Test
    public void transformTest() {
        assertEquals(new CutGoal(), this.goal.transform(new Indexifier(42)));
    }

    /**
     * A CutGoal never has any variables in it.
     */
    @Test
    public void getVariablesTest() {
        assertEquals(Collections.emptySet(), this.goal.getVariables());
    }

    /**
     * Tests the createActivationRecord method through the Interpreter.
     */
    @Test
    public void createActivationRecordTest() {
        Interpreter interpreter = new Interpreter(new KnowledgeBase(Arrays.asList()), this.goal);

        assertEquals(this.goal, interpreter.getQuery().getGoal());
    }
}
