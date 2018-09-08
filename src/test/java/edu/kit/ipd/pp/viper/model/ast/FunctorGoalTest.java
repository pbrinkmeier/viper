package edu.kit.ipd.pp.viper.model.ast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

public class FunctorGoalTest {
    private FunctorGoal testGoal;

    /**
     * Initializes the functor goal used in the tests.
     */
    @Before
    public void init() {
        this.testGoal = new FunctorGoal(new Functor("test", Arrays.asList(Functor.atom("a"), Functor.atom("b"))));
    }

    /**
     * Tests the Getter-Function for the Functor of a Functor-Goal.
     */
    @Test
    public void getFunctorTest() {
        assertEquals(new Functor("test", Arrays.asList(Functor.atom("a"), Functor.atom("b"))),
                this.testGoal.getFunctor());
        assertNotEquals(Functor.atom("test"), this.testGoal.getFunctor());
    }

    /**
     * Makes sure that the getVariables method returns a set of variables from all parameters.
     */
    @Test
    public void getVariablesTest() {
        FunctorGoal withVars
            = new FunctorGoal(new Functor("nice", Arrays.asList(new Variable("X"), new Variable("Y"))));
        
        assertEquals(new HashSet<>(Arrays.asList(new Variable("X"), new Variable("Y"))), withVars.getVariables());
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.testGoal, null);
        assertNotEquals(this.testGoal, new Object());
    }

    /**
     * Tests the hashCode method, makes sure it is implemented using Objects.hash.
     */
    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash(this.testGoal.getFunctor()), this.testGoal.hashCode());
    }
}
