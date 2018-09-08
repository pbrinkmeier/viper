package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class ComparisonGoalTest {
    private ComparisonGoal goal;

    /**
     * Initializes a the goal "X =:= Y" before each test.
     * EqualGoal is a subgoal of ComparisonGoal and is used to test the common methods of all ComparisonGoals.
     */
    @Before
    public void init() {
        this.goal = new EqualGoal(new Variable("X"), new Variable("Y"));
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.goal, null);
        assertNotEquals(this.goal, new Object());
        assertNotEquals(this.goal, new EqualGoal(new Variable("Z"), new Variable("Y")));
        assertNotEquals(this.goal, new EqualGoal(new Variable("X"), new Variable("Z")));
        assertEquals(new EqualGoal(new Variable("X"), new Variable("Y")), this.goal);
    }

    /**
     * Makes sure that hashCode is implemented using Objects.hash
     */
    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash("=:=", new Variable("X"), new Variable("Y")), this.goal.hashCode());
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void toStringTest() {
        assertEquals("X =:= Y", this.goal.toString());
    }

    /**
     * Tests the method for getting the HTML version of the comparisons symbol.
     */
    @Test
    public void getHtmlSymbolTest() {
        assertEquals("=:=", this.goal.getHtmlSymbol());
    }

    /**
     * Tests the getVariables method.
     */
    @Test
    public void getVariablesTest() {
        assertEquals(new HashSet<>(Arrays.asList(new Variable("X"), new Variable("Y"))), this.goal.getVariables());
    }

    /**
     * Tests the correct creation of activation records.
     * This is easier to do indirectly like below because an Interpreter would need to be passed anyways.
     */
    @Test
    public void createActivationRecordTest() {
        Interpreter interpreter = new Interpreter(new KnowledgeBase(Arrays.asList()), this.goal);
        assertEquals(this.goal, interpreter.getQuery().getGoal());
    }
}
