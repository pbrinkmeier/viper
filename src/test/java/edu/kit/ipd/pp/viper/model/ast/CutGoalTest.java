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

    @Before
    public void init() {
        this.goal = new CutGoal();
    }

    @Test
    public void equalsTest() {
        assertNotEquals(this.goal, null);
        assertNotEquals(this.goal, new Object());
        assertEquals(this.goal, new CutGoal());
    }

    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash("!"), this.goal.hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals("!", this.goal.toString());
    }

    @Test
    public void transformTest() {
        assertEquals(new CutGoal(), this.goal.transform(new Indexifier(42)));
    }

    @Test
    public void getVariablesTest() {
        assertEquals(Collections.EMPTY_SET, this.goal.getVariables());
    }

    @Test
    public void createActivationRecordTest() {
        Interpreter interpreter = new Interpreter(new KnowledgeBase(Arrays.asList()), this.goal);

        assertEquals(this.goal, interpreter.getQuery().getGoal());
    }
}
