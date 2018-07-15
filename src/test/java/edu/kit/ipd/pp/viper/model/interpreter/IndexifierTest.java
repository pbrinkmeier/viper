package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.interpreter.Indexifier;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class IndexifierTest {
    @Test
    public void visitFunctorTest() {
        Functor fun = new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z")));

        assertEquals(new Functor("father", Arrays.asList(new Variable("X", 42), new Variable("Z", 42))),
                fun.transform(new Indexifier(42)));
    }

    @Test
    public void visitVariableTest() {
        Variable var = new Variable("A");

        assertEquals(new Variable("A", 42), var.transform(new Indexifier(42)));
    }

    @Test
    public void visitNumberTest() {
        Number num = new Number(100);

        assertEquals(new Number(100), num.transform(new Indexifier(42)));
    }
}
