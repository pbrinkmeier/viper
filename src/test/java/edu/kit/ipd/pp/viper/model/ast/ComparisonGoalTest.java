package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class ComparisonGoalTest {
    private ComparisonGoal goal;

    @Before
    public void init() {
        this.goal = new EqualGoal(new Variable("X"), new Variable("Y"));
    }

    @Test
    public void equalsTest() {
        assertNotEquals(this.goal, null);
        assertNotEquals(this.goal, new Object());
        assertNotEquals(this.goal, new EqualGoal(new Variable("Z"), new Variable("Y")));
        assertNotEquals(this.goal, new EqualGoal(new Variable("X"), new Variable("Z")));
        assertEquals(new EqualGoal(new Variable("X"), new Variable("Y")), this.goal);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash("=:=", new Variable("X"), new Variable("Y")), this.goal.hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals("X =:= Y", this.goal.toString());
    }

    @Test
    public void getHtmlSymbolTest() {
        assertEquals("=:=", this.goal.getHtmlSymbol());
    }

    @Test
    public void getVariablesTest() {
        assertEquals(new HashSet<>(Arrays.asList(new Variable("X"), new Variable("Y"))), this.goal.getVariables());
        assertEquals(new HashSet<>(Arrays.asList(new Variable("X"))), new EqualGoal(new Variable("X"), new Variable("X")).getVariables());
    }

    @Test
    public void createActivationRecordTest() {
        Interpreter interpreter = new Interpreter(new KnowledgeBase(Arrays.asList()), this.goal);
        assertEquals(this.goal, interpreter.getQuery().getGoal());
    }
}
