package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;

public class TermTest {
    @Test
    public void subClassesTest() {
        Term fun = new Functor("test", Arrays.asList());
        Term var = new Variable("X");
        Term num = new Number(42);
    }
}
