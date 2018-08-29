package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class SubstitutionTest {
    private Substitution substitution;

    /**
     * Initializes the substitution used in the tests.
     */
    @Before
    public void init() {
        this.substitution = new Substitution(new Variable("X"), Functor.atom("homer"));
    }

    /**
     * Tests the Getter-Method for the replacement term.
     */
    @Test
    public void getReplaceTest() {
        assertEquals(new Variable("X"), this.substitution.getReplace());
    }

    /**
     * Tests the Getter-Method for the replacer term.
     */
    @Test
    public void getByTest() {
        assertEquals(Functor.atom("homer"), this.substitution.getBy());
    }

    /**
     * Tests the comparison of two substitutions.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.substitution, null);
        assertNotEquals(this.substitution, new Object());

        assertNotEquals(new Substitution(new Variable("Y"), Functor.atom("homer")), this.substitution);
        assertNotEquals(new Substitution(new Variable("X"), Functor.atom("bart")), this.substitution);
        assertEquals(new Substitution(new Variable("X"), Functor.atom("homer")), this.substitution);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(
            Objects.hash(this.substitution.getReplace(), this.substitution.getBy()),
            this.substitution.hashCode()
        );
    }
}
