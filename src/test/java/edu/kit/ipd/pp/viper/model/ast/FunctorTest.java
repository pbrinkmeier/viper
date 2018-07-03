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
                Functor.atom("a"),
                Functor.atom("b"),
                Functor.atom("c")
            ));
        
        this.noParam =
            Functor.atom("noparam");
    }

    @Test
    public void atomTest() {
        assertEquals(new Functor("test", Arrays.asList()), Functor.atom("test"));
        assertEquals(new Functor("test2", Arrays.asList()), Functor.atom("test2"));
        assertEquals(new Functor("pls", Arrays.asList()), Functor.atom("pls"));
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
                Functor.atom("a"),
                Functor.atom("b"),
                Functor.atom("c")
            ));

        assertEquals(testFunctor, this.fun);

        assertNotEquals(testFunctor, this.noParam);
        assertNotEquals(this.fun, this.noParam);

        // two atoms should be equal if their names are equal
        assertEquals(Functor.atom("pls"), Functor.atom("pls"));
        assertNotEquals(Functor.atom("pls"), Functor.atom("no"));
        
        // two functors should not be equal if they have a different number of parameters (even if their names are equal)
        assertNotEquals(Functor.atom("pls"), new Functor("pls", Arrays.asList(Functor.atom("can"))));
        assertNotEquals(new Functor("has", Arrays.asList(Functor.atom("cheezburger"))), Functor.atom("has"));
        assertNotEquals(
            new Functor("two", Arrays.asList(Functor.atom("a"), Functor.atom("b"))),
            new Functor("two", Arrays.asList(Functor.atom("a")))
        );

        // two functors should not be equal if their parameters are not equal (even if their names and parameter count are equal)
        assertNotEquals(
            new Functor("delet", Arrays.asList(Functor.atom("yu"))),
            new Functor("delet", Arrays.asList(Functor.atom("dis")))
        );
        // even if their parameters are partially equal
        assertNotEquals(
            new Functor("father", Arrays.asList(Functor.atom("homer"), Functor.atom("bart"))),
            new Functor("father", Arrays.asList(Functor.atom("homer"), Functor.atom("lisa")))
        );

        // two functors should be equal if their names, parameter count and parameters are equal
        assertEquals(
            new Functor("mother", Arrays.asList(Functor.atom("marge"), Functor.atom("bart"))),
            new Functor("mother", Arrays.asList(Functor.atom("marge"), Functor.atom("bart")))
        );
    }

    @Test
    public void getParametersTest() throws Exception {
        assertEquals(
            Arrays.asList(
                Functor.atom("a"),
                Functor.atom("b"),
                Functor.atom("c")
            ),
            this.fun.getParameters()
        );
    }

    @Test
    public void createNewTest() {
        assertEquals(
            new Functor("test", Arrays.asList(
                Functor.atom("single")
            )),
            this.fun.createNew(Arrays.asList(
                Functor.atom("single")
            ))
        );
    }
}
