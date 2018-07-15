package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class RuleTest {
    private Rule testRule;

    @Before
    public void init() {
        this.testRule = new Rule(new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y"))),
                Arrays.asList(
                        new FunctorGoal(new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z")))),
                        new FunctorGoal(new Functor("father", Arrays.asList(new Variable("Z"), new Variable("Y"))))));
    }

    @Test
    public void getHeadTest() {
        assertEquals(new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y"))),
                this.testRule.getHead());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSubgoalsTest() throws UnsupportedOperationException {
        this.testRule.getSubgoals().add(null);
    }

    @Test
    public void toStringTest() {
        assertEquals("grandfather(X, Y) :- \n  father(X, Z),\n  father(Z, Y).", this.testRule.toString());
    }
}
