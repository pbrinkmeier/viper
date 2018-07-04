package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class UnificationTest {
    @Test
    public void simpleTest() {
        Term lhs = Functor.atom("cheezburger");
        Term rhs = new Variable("Foond");

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));

        assertTrue(result.isSuccess());
        assertEquals(
            Arrays.asList(new Substitution(new Variable("Foond"), Functor.atom("cheezburger"))),
            result.getSubstitutions()
        );
    }

    @Test
    public void functorTest() {
        Term lhs = new Functor("father", Arrays.asList(new Variable("X"), new Variable("Y")));
        Term rhs = new Functor("father", Arrays.asList(Functor.atom("abe"), Functor.atom("homer")));

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));

        assertTrue(result.isSuccess());
        assertEquals(
            Arrays.asList(
                new Substitution(new Variable("X"), Functor.atom("abe")),
                new Substitution(new Variable("Y"), Functor.atom("homer"))
            ),
            result.getSubstitutions()
        );
    }

    @Test
    public void failTest() {
        Term lhs = Functor.atom("homer");
        Term rhs = Functor.atom("marge");

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));

        assertTrue(!result.isSuccess());
        assertEquals("homer ≠ marge", result.getErrorMessage());
    }

    @Test
    public void variableUsedMultipleTimesTest() {
        Term lhs = new Functor("max", Arrays.asList(
            new Number(42),
            new Number(17),
            new Variable("Maximum", 1)
        ));

        Term rhs = new Functor("max", Arrays.asList(
            new Variable("X"),
            new Variable("Y"),
            new Variable("X")
        ));

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));
        
        assertTrue(result.isSuccess());
        assertEquals(
            Arrays.asList(
                new Substitution(new Variable("X"), new Number(42)),
                new Substitution(new Variable("Y"), new Number(17)),
                new Substitution(new Variable("Maximum", 1), new Number(42))
            ),
            result.getSubstitutions()
        );
    }

    @Test
    // Unification, although "working both ways", has a direction in our model.
    // The direction determines which sides variables are "stronger".
    // Stronger Variables propagate deeper; in our model, the left side is stronger.
    // This means that when two variables are unified, the left one is not going the be replaced;
    // it replaces the right one.
    public void directionTest() {
        Term lhs = new Variable("X");
        Term rhs = new Variable("Y");

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));

        assertTrue(result.isSuccess());
        assertEquals(
            Arrays.asList(new Substitution(new Variable("Y"), new Variable("X"))),
            result.getSubstitutions()
        );
    }

    // Unifying a variable with itself should not yield any substitutions
    @Test
    public void avoidSelfSubstitutionTest() {
        Term lhs = new Variable("X");
        Term rhs = new Variable("X");

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));

        assertTrue(result.isSuccess());
        assertEquals(Arrays.asList(), result.getSubstitutions());
    }

    @Test
    public void avoidRecursiveSubstitutionTest() {
        Term lhs = new Variable("X");
        Term rhs = new Functor("f", Arrays.asList(new Variable("X")));

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));

        assertTrue(!result.isSuccess());
        assertEquals("X ≠ f(X)", result.getErrorMessage());
    }
}
