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

    @Before
    public void init() {
        List<Substitution> substitutions = Arrays.asList(
            new Substitution(new Variable("X"), Functor.atom("abe")),
            new Substitution(new Variable("Y"), Functor.atom("bart"))
        );

        this.successResult = UnificationResult.success(substitutions);
        this.failResult = UnificationResult.fail(Functor.atom("abe"), Functor.atom("homer"));
    }

    @Test
    public void isSuccessTest() {
        assertTrue(this.successResult.isSuccess());
        assertTrue(!this.failResult.isSuccess());
    }

    @Test
    public void getSubstitutionsSuccessTest() {
        List<Substitution> substitutions = Arrays.asList(
            new Substitution(new Variable("X"), Functor.atom("abe")),
            new Substitution(new Variable("Y"), Functor.atom("bart"))
        );

        assertEquals(substitutions, this.successResult.getSubstitutions());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSubstitutionsImmutableTest() {
        List<Substitution> substitutions = this.successResult.getSubstitutions();

        substitutions.add(new Substitution(new Variable("I"), Functor.atom("shouldfail")));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSubstitutionsFailTest() {
        this.failResult.getSubstitutions();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getErrorMessageSuccessTest() {
        this.successResult.getErrorMessage();
    }

    @Test
    public void getErrorMessageFailTest() {
        assertEquals("abe ≠ homer", this.failResult.getErrorMessage());
    }

    @Test
    public void toHtmlTest() {
        assertEquals("X ⇒ abe, Y ⇒ bart", this.successResult.toHtml());
        assertEquals("abe ≠ homer", this.failResult.toHtml());
    }
}
