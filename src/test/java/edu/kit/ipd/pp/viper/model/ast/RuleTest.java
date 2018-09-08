package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class RuleTest {
    private Rule testRule;

    /**
     * Initializes the rule used in the tests.
     */
    @Before
    public void init() {
        this.testRule = new Rule(new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y"))),
                Arrays.asList(
                        new FunctorGoal(new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z")))),
                        new FunctorGoal(new Functor("father", Arrays.asList(new Variable("Z"), new Variable("Y"))))));
    }

    /**
     * Tests the Getter-Method for the head of the rule.
     */
    @Test
    public void getHeadTest() {
        assertEquals(new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y"))),
                this.testRule.getHead());
    }

    /**
     * Tests whether adding null to the subgoals raises a proper exception.
     * 
     * @throws UnsupportedOperationException if adding null fails properly
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getSubgoalsTest() throws UnsupportedOperationException {
        this.testRule.getSubgoals().add(null);
    }

    /**
     * Tests the conversion from a Rule to a String.
     */
    @Test
    public void toStringTest() {
        assertEquals("grandfather(X, Y) :-\n  father(X, Z),\n  father(Z, Y).", this.testRule.toString());
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.testRule, null);
        assertNotEquals(this.testRule, new Object());

        // Two rules should not be equal if their heads differ
        assertNotEquals(this.testRule, new Rule(Functor.atom("fail"), Arrays.asList()));

        // Two rules should not be equal if their heads are the same but their subgoals differ
        Rule sameHead = new Rule(this.testRule.getHead(), Arrays.asList());
        assertNotEquals(this.testRule, sameHead);
    }

    /**
     * Tests whether the hashCode is correctly generated using the helper method in Objects.
     */
    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash(this.testRule.getHead(), this.testRule.getSubgoals()), this.testRule.hashCode());
    }
}
