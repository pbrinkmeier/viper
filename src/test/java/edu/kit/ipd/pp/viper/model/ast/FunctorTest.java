package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class FunctorTest {
    private Functor fun;
    private Functor noParam;

    @Before
    public void init() {
        this.fun =
            new Functor("test", Arrays.asList(
                new Functor("a", Arrays.asList()),
                new Functor("b", Arrays.asList()),
                new Functor("c", Arrays.asList())
            ));
        
        this.noParam =
            new Functor("noparam", Arrays.asList());
    }

    @Test
    public void toStringTest() {
        assertEquals("noparam", this.noParam.toString());
        assertEquals("test(a, b, c)", this.fun.toString());
    }

    @Test(expected = UnsupportedTermException.class)
    public void evaluateTest() throws UnsupportedTermException {
        this.fun.evaluate();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getParametersImmutableTest() throws UnsupportedOperationException {
        List<Term> param = this.fun.getParameters();
        param.add(this.fun);
    }

    @Test
    public void getNameTest() {
        assertEquals("test", this.fun.getName());
        assertEquals("noparam", this.noParam.getName());
    }

    // TODO this test is a little bit tricky, since we don't have an equals() method on term/functor (yet)
    @Test
    public void getParametersTest() throws Exception {
        throw new Exception("Not yet implemented");
    }
}
