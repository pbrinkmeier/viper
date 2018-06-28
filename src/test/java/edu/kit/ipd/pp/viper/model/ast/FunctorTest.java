package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class FunctorTest {
    private Functor fun;

    @Before
    public void init() {
        this.fun =
            new Functor("test", Arrays.asList(
                new Functor("a", Arrays.asList()),
                new Functor("b", Arrays.asList()),
                new Functor("c", Arrays.asList())
            ));
    }

    @Test
    public void noParameterToStringTest() {
        Functor fun = new Functor("test", Arrays.asList());

        assertEquals(fun.toString(), "test");
    }

    @Test
    public void parameterToStringTest() {
        assertEquals("test(a, b, c)", this.fun.toString());
    }

    @Test(expected = UnsupportedTermException.class)
    public void evaluationTest() throws TermEvaluationException {
        this.fun.evaluate();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void parametersAreImmutableTest() throws UnsupportedOperationException {
        List<Term> param = this.fun.getParameters();
        param.add(this.fun);
    }

    @Test
    public void canGetName() {
        assertEquals("test", this.fun.getName());
    }

    // TODO this test is a little bit tricky, since we don't have an equals() method on term/functor (yet)
    @Test
    public void canGetParameters() throws Exception {
        throw new Exception("Not yet implemented");
    }
}
