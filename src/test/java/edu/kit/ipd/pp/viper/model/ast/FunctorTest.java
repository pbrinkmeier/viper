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

    @Test
    public void toHtmlTest() {
        assertEquals("test(a, b, c)", this.fun.toHtml());
        assertEquals("noparam", this.noParam.toHtml());

        // make sure that toHtml also uses toHtml() of the functors parameters
        assertEquals(
            "index(X<sub>42</sub>)",
            new Functor("index", Arrays.asList(new Variable("X", 42))).toHtml()
        );
    }

    @Test(expected = UnsupportedTermException.class)
    public void evaluateTest() throws TermEvaluationException {
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

    @Test
    public void equalsTest() {
        Functor testFunctor =
            new Functor("test", Arrays.asList(
                new Functor("a", Arrays.asList()),
                new Functor("b", Arrays.asList()),
                new Functor("c", Arrays.asList())
            ));

        assertEquals(testFunctor, this.fun);

        assertNotEquals(testFunctor, this.noParam);
        assertNotEquals(this.fun, this.noParam);

        // two atoms should be equal if their names are equal
        assertEquals(new Functor("pls", Arrays.asList()), new Functor("pls", Arrays.asList()));
        assertNotEquals(new Functor("pls", Arrays.asList()), new Functor("no", Arrays.asList()));
        
        // two functors should not be equal if they have a different number of parameters (even if their names are equal)
        assertNotEquals(new Functor("pls", Arrays.asList()), new Functor("pls", Arrays.asList(new Functor("can", Arrays.asList()))));
        assertNotEquals(new Functor("has", Arrays.asList(new Functor("cheezburger", Arrays.asList()))), new Functor("has", Arrays.asList()));
        assertNotEquals(
            new Functor("two", Arrays.asList(new Functor("a", Arrays.asList()), new Functor("b", Arrays.asList()))),
            new Functor("two", Arrays.asList(new Functor("a", Arrays.asList())))
        );

        // two functors should not be equal if their parameters are not equal (even if their names and parameter count are equal)
        assertNotEquals(
            new Functor("delet", Arrays.asList(new Functor("yu", Arrays.asList()))),
            new Functor("delet", Arrays.asList(new Functor("dis", Arrays.asList())))
        );
        // even if their parameters are partially equal
        assertNotEquals(
            new Functor("father", Arrays.asList(new Functor("homer", Arrays.asList()), new Functor("bart", Arrays.asList()))),
            new Functor("father", Arrays.asList(new Functor("homer", Arrays.asList()), new Functor("lisa", Arrays.asList())))
        );

        // two functors should be equal if their names, parameter count and parameters are equal
        assertEquals(
            new Functor("mother", Arrays.asList(new Functor("marge", Arrays.asList()), new Functor("bart", Arrays.asList()))),
            new Functor("mother", Arrays.asList(new Functor("marge", Arrays.asList()), new Functor("bart", Arrays.asList())))
        );
    }

    @Test
    public void getParametersTest() throws Exception {
        assertEquals(
            Arrays.asList(
                new Functor("a", Arrays.asList()),
                new Functor("b", Arrays.asList()),
                new Functor("c", Arrays.asList())
            ),
            this.fun.getParameters()
        );
    }

    @Test
    public void createNewTest() {
        assertEquals(
            new Functor("test", Arrays.asList(
                new Functor("single", Arrays.asList())
            )),
            this.fun.createNew(Arrays.asList(
                new Functor("single", Arrays.asList())
            ))
        );
    }
}
