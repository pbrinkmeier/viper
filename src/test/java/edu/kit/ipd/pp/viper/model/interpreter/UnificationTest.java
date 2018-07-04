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

        UnificationResult result = lhs.accept(rhs.accept(new UnifierCreator()));

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

        UnificationResult result = lhs.accept(rhs.accept(new UnifierCreator()));

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

        UnificationResult result = lhs.accept(rhs.accept(new UnifierCreator()));

        assertTrue(!result.isSuccess());
        assertEquals("marge ≠ homer", result.getErrorMessage());
    }

    @Test
    public void variableUsedMultipleTimesTest() {
        Term lhs = new Functor("max", Arrays.asList(
            new Variable("X"),
            new Variable("Y"),
            new Variable("X")
        ));

        Term rhs = new Functor("max", Arrays.asList(
            new Number(42),
            new Number(17),
            new Variable("Maximum", 1)
        ));

        UnificationResult result = lhs.accept(rhs.accept(new UnifierCreator()));
        
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
}
