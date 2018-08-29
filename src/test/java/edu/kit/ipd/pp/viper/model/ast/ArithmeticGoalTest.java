package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Arrays;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class ArithmeticGoalTest {
    private ArithmeticGoal goal;
    private ArithmeticGoal uselessGoal;

    @Before
    public void init() {
        this.goal =
            new ArithmeticGoal(new Variable("X"), new AdditionOperation(new Number(42), new Variable("Y")));
        this.uselessGoal =
            new ArithmeticGoal(new Variable("Z"), new Variable("Z"));
    }

    @Test
    public void getVariablesTest() {
        assertEquals(Arrays.asList(new Variable("X"), new Variable("Y")), this.goal.getVariables());
        assertEquals(Arrays.asList(new Variable("Z")), this.uselessGoal.getVariables());
    }

    @Test
    public void equalsTest() {
        assertNotEquals(this.goal, null);
        assertNotEquals(this.goal, new Object());
        assertNotEquals(this.goal, this.uselessGoal);
        assertNotEquals(new ArithmeticGoal(new Variable("X"), new Number(42)), this.goal);
        assertEquals(
            new ArithmeticGoal(new Variable("X"), new AdditionOperation(new Number(42), new Variable("Y"))),
            this.goal
        );
    }

    @Test
    public void toStringTest() {
        assertEquals("X is (42 + Y)", this.goal.toString());
    }

    @Test
    public void transformTest() {
        assertEquals(
            new ArithmeticGoal(new Variable("Z", 42), new Variable("Z", 42)),
            this.uselessGoal.transform(new Indexifier(42))
        );
    }

    @Test
    public void createActivationRecordTest() {
        Interpreter interpreter =
            new Interpreter(new KnowledgeBase(Arrays.asList()), this.goal);
        
        assertEquals(this.goal, interpreter.getQuery().getGoal());
    }

    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash(this.goal.getLhs(), this.goal.getRhs()), this.goal.hashCode());
    }
}
