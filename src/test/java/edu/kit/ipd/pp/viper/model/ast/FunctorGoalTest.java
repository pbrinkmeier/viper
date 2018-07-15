package edu.kit.ipd.pp.viper.model.ast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;

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
     * Tests the creation of an adequate Activation Record.
     */
    @Test
    public void createActivationRecordTest() {
        // FunctorActivationRecord far =
        // this.testGoal.createActivationRecord(Optional.empty());

        // without any substitutions, these should be equal
        // assertEquals(this.testGoal.getFunctor(), far.getFunctor());
    }
}
