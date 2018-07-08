package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import org.junit.*;
import static org.junit.Assert.*;

public class SubstitutionTest {
    private Substitution substitution;

    @Before
    public void init() {
        this.substitution = new Substitution(new Variable("X"), Functor.atom("homer"));
    }

    @Test
    public void getReplaceTest() {
        assertEquals(new Variable("X"), this.substitution.getReplace());
    }

    @Test
    public void getByTest() {
        assertEquals(Functor.atom("homer"), this.substitution.getBy());
    }

    @Test
    public void equalsTest() {
        assertNotEquals(new Substitution(new Variable("Y"), Functor.atom("homer")), this.substitution);
        assertNotEquals(new Substitution(new Variable("X"), Functor.atom("bart")), this.substitution);
        assertEquals(new Substitution(new Variable("X"), Functor.atom("homer")), this.substitution);
    }
}
