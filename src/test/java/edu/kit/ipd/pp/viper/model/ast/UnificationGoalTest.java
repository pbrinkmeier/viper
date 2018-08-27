package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class UnificationGoalTest {
    private Goal goal;
    private Goal uselessGoal;

    @Before
    public void init() {
        this.goal =
            new UnificationGoal(new Variable("X"), new Variable("Y"));
        this.uselessGoal =
            new UnificationGoal(
                new Functor("tuple", Arrays.asList(new Variable("X"), new Variable("X"))),
                new Functor("tuple", Arrays.asList(new Variable("X"), new Variable("X")))
            );
    }

    @Test
    public void getVariablesTest() {
        assertEquals(Arrays.asList(new Variable("X"), new Variable("Y")), this.goal.getVariables());
        assertEquals(Arrays.asList(new Variable("X")), this.uselessGoal.getVariables());
    }

    @Test
    public void equalsTest() {
        assertNotEquals(this.goal, null);
        assertNotEquals(this.goal, new Object());
        assertNotEquals(new UnificationGoal(new Variable("X"), new Variable("Z")), this.goal);
        assertNotEquals(new UnificationGoal(new Variable("Y"), new Variable("X")), this.goal);
        assertEquals(new UnificationGoal(new Variable("X"), new Variable("Y")), this.goal);
    }

    @Test
    public void transformTest() {
        assertEquals(
            new UnificationGoal(new Variable("X", 42), new Variable("Y", 42)),
            this.goal.transform(new Indexifier(42))
        );
    }

    @Test
    public void toStringTest() {
        assertEquals("X = Y", this.goal.toString());
    }

    @Test
    public void createActivationRecordTest() {
        Interpreter interpreter =
            new Interpreter(new KnowledgeBase(Arrays.asList()), this.goal);
        
        assertEquals(this.goal, interpreter.getQuery().getGoal());
    }
}
