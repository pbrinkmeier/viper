package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;

import java.util.Arrays;
import java.util.Optional;

import org.junit.*;
import static org.junit.Assert.*;

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
        FunctorActivationRecord far = this.testGoal.createActivationRecord(Optional.empty());

        // without any substitutions, these should be equal
        assertEquals(this.testGoal.getFunctor(), far.getFunctor());
    }
}
