package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;

// This test wont even compile without all its subclasses present
public class TermTest {
    /**
     * Tests the creation of all subclasses of Term.
     */
    @SuppressWarnings("unused")
    @Test
    public void subClassesTest() {
        Term fun = new Functor("test", Arrays.asList());
        Term var = new Variable("X");
        Term num = new Number(42);
        Term plus = new AdditionOperation(new Number(40), new Number(2));
        Term minus = new SubtractionOperation(new Number(100), new Number(58));
        Term times = new MultiplicationOperation(new Number(6), new Number(7));
    }
}
