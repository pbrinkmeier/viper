package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class FunctorTest {
    private Functor fun;
    private Functor noParam;
    private Functor validList;
    private Functor invalidList;
    private Functor invalidCons;

    /**
     * Initializes the Functors used in the tests.
     */
    @Before
    public void init() {
        this.fun = new Functor("test", Arrays.asList(Functor.atom("a"), Functor.atom("b"), Functor.atom("c")));
        this.noParam = Functor.atom("noparam");

        this.validList =
            new Functor("[|]", Arrays.asList(new Number(1),
            new Functor("[|]", Arrays.asList(new Number(2),
            new Functor("[|]", Arrays.asList(new Number(3),
            Functor.atom("[]")))))));

        this.invalidList =
            new Functor("[|]", Arrays.asList(new Number(1),
            new Functor("[|]", Arrays.asList(new Number(2),
            new Number(3)))));

        this.invalidCons = Functor.atom("[|]");
    }

    /**
     * Tests the static atom-Function in comparison to using the constructor.
     */
    @Test
    public void atomTest() {
        assertEquals(new Functor("test", Arrays.asList()), Functor.atom("test"));
        assertEquals(new Functor("test2", Arrays.asList()), Functor.atom("test2"));
        assertEquals(new Functor("pls", Arrays.asList()), Functor.atom("pls"));
    }

    /**
     * Tests the conversion of a Functor to a String.
     */
    @Test
    public void toStringTest() {
        assertEquals("noparam", this.noParam.toString());
        assertEquals("test(a, b, c)", this.fun.toString());

        assertEquals("[1 | [2 | 3]]", this.invalidList.toString());
        assertEquals("[1, 2, 3]", this.validList.toString());
        assertEquals("[|]", this.invalidCons.toString());
    }

    /**
     * Tests the conversion of a Functor to HTML code.
     */
    @Test
    public void toHtmlTest() {
        assertEquals("test(a, b, c)", this.fun.toHtml());
        assertEquals("noparam", this.noParam.toHtml());

        assertEquals("[1 &#124; [2 &#124; 3]]", this.invalidList.toHtml());
        assertEquals("[1, 2, 3]", this.validList.toHtml());
        assertEquals("[|]", this.invalidCons.toHtml());
    }

    /**
     * Tests the functor evaluation method.
     * 
     * @throws TermEvaluationException if the evaluation fails
     */
    @Test(expected = UnsupportedTermException.class)
    public void evaluateTest() throws TermEvaluationException {
        this.fun.evaluate();
    }

    /**
     * Tests whether the getParameters()-Method actually returns an immutable list.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getParametersImmutableTest() {
        List<Term> param = this.fun.getParameters();
        param.add(this.fun);
    }

    /**
     * Tests the Getter-Method for the Functor name.
     */
    @Test
    public void getNameTest() {
        assertEquals("test", this.fun.getName());
        assertEquals("noparam", this.noParam.getName());
    }

    /**
     * Tests Functor-comparison.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.fun, null);
        assertNotEquals(this.fun, new Object());

        Functor testFunctor = new Functor("test",
                Arrays.asList(Functor.atom("a"), Functor.atom("b"), Functor.atom("c")));

        assertEquals(testFunctor, this.fun);

        assertNotEquals(testFunctor, this.noParam);
        assertNotEquals(this.fun, this.noParam);

        // two atoms should be equal if their names are equal
        assertEquals(Functor.atom("pls"), Functor.atom("pls"));
        assertNotEquals(Functor.atom("pls"), Functor.atom("no"));

        // two functors should not be equal if they have a different number of
        // parameters (even if their names are equal)
        assertNotEquals(Functor.atom("pls"), new Functor("pls", Arrays.asList(Functor.atom("can"))));
        assertNotEquals(new Functor("has", Arrays.asList(Functor.atom("cheezburger"))), Functor.atom("has"));
        assertNotEquals(new Functor("two", Arrays.asList(Functor.atom("a"), Functor.atom("b"))),
                new Functor("two", Arrays.asList(Functor.atom("a"))));

        // two functors should not be equal if their parameters are not equal (even if
        // their names and parameter count are equal)
        assertNotEquals(new Functor("delet", Arrays.asList(Functor.atom("yu"))),
                new Functor("delet", Arrays.asList(Functor.atom("dis"))));
        // even if their parameters are partially equal
        assertNotEquals(new Functor("father", Arrays.asList(Functor.atom("homer"), Functor.atom("bart"))),
                new Functor("father", Arrays.asList(Functor.atom("homer"), Functor.atom("lisa"))));

        // two functors should be equal if their names, parameter count and parameters
        // are equal
        assertEquals(new Functor("mother", Arrays.asList(Functor.atom("marge"), Functor.atom("bart"))),
                new Functor("mother", Arrays.asList(Functor.atom("marge"), Functor.atom("bart"))));
    }

    /**
     * Tests the Getter-Method for the Functor parameter list.
     */
    @Test
    public void getParametersTest() {
        assertEquals(Arrays.asList(Functor.atom("a"), Functor.atom("b"), Functor.atom("c")), this.fun.getParameters());
    }

    /**
     * Tests the creation of a new Functor via an already instantiated Functor
     * object.
     */
    @Test
    public void createNewTest() {
        assertEquals(new Functor("test", Arrays.asList(Functor.atom("single"))),
                this.fun.createNew(Arrays.asList(Functor.atom("single"))));
    }

    @Test
    public void matchesTest() {
        assertTrue(this.fun.matches(new Functor("test", Arrays.asList(new Number(1), new Number(2), new Number(3)))));
        assertFalse(this.fun.matches(Functor.atom("test")));
    }
}
