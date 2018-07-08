package edu.kit.ipd.pp.viper.model.ast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class FunctorGoalTest {
    private FunctorGoal testGoal;

    @Before
    public void init() {
        this.testGoal = new FunctorGoal(new Functor("test", Arrays.asList(Functor.atom("a"), Functor.atom("b"))));
    }

    @Test
    public void getFunctorTest() {
        assertEquals(new Functor("test", Arrays.asList(Functor.atom("a"), Functor.atom("b"))), this.testGoal.getFunctor());
        assertNotEquals(Functor.atom("test"), this.testGoal.getFunctor());
    }

    @Test
    public void createActivationRecordTest() {
        // FunctorActivationRecord far = this.testGoal.createActivationRecord(Optional.empty());

        // without any substitutions, these should be equal
        // assertEquals(this.testGoal.getFunctor(), far.getFunctor());
    }
}
