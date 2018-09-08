package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.*;
import static org.junit.Assert.*;

public class VariableExtractorTest {
    /**
     * Tests the proper extraction of variables from a term.
     */
    @Test
    public void extractFromFunctorTest() {
        Term term = new Functor("blub", Arrays.asList(new Number(42), new Variable("X"),
                new Functor("sub", Arrays.asList(new Variable("X"), new Variable("Y")))));

        Set<Variable> variables = term.accept(new VariableExtractor());
        assertEquals(new HashSet<>(Arrays.asList(new Variable("X"), new Variable("Y"))), variables);
    }
}
