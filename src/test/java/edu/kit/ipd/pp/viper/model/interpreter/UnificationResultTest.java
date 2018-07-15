package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class UnificationResultTest {
    private UnificationResult successResult;
    private UnificationResult failResult;

    /**
     * Initializes the UnificationResults used for the tests.
     */
    @Before
    public void init() {
        List<Substitution> substitutions = Arrays.asList(new Substitution(new Variable("X"), Functor.atom("abe")),
                new Substitution(new Variable("Y"), Functor.atom("bart")));

        this.successResult = UnificationResult.success(substitutions);
        this.failResult = UnificationResult.fail(Functor.atom("abe"), Functor.atom("homer"));
    }

    /**
     * Tests the Getter-Method for the success of a unification.
     */
    @Test
    public void isSuccessTest() {
        assertTrue(this.successResult.isSuccess());
        assertTrue(!this.failResult.isSuccess());
    }

    /**
     * Tests the Getter-Method for the substitutions of a successful unification.
     */
    @Test
    public void getSubstitutionsSuccessTest() {
        List<Substitution> substitutions = Arrays.asList(new Substitution(new Variable("X"), Functor.atom("abe")),
                new Substitution(new Variable("Y"), Functor.atom("bart")));

        assertEquals(substitutions, this.successResult.getSubstitutions());
    }

    /**
     * Tests whether the substitutions returned by a unification result
     * are properly immutable.
     * 
     * @throws UnsupportedOperationException if the returned substitution list is immutable
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getSubstitutionsImmutableTest() {
        List<Substitution> substitutions = this.successResult.getSubstitutions();

        substitutions.add(new Substitution(new Variable("I"), Functor.atom("shouldfail")));
    }

    /**
     * Tests whether getting the substitutions of a failed unification properly throws an exception.
     * 
     * @throws UnsupportedOperationException if the failed unification can't return any substitutions
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getSubstitutionsFailTest() {
        this.failResult.getSubstitutions();
    }

    /**
     * Tests whether a successful unification properly raises an exception when asked for an error message.
     * 
     * @throws UnsupportedOperationException if the succesful unification can't return an error message
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getErrorMessageSuccessTest() {
        this.successResult.getErrorMessage();
    }

    /**
     * Tests the Getter-Method for the error message of a failed unification.
     */
    @Test
    public void getErrorMessageFailTest() {
        assertEquals("abe is not unifiable with homer", this.failResult.getErrorMessage());
    }

    /**
     * Tests the conversion of a unification result to HTML code.
     */
    @Test
    public void toHtmlTest() {
        assertEquals("X =&gt; abe, Y =&gt; bart", this.successResult.toHtml());
        assertEquals("abe is not unifiable with homer", this.failResult.toHtml());
    }
}
