package edu.kit.ipd.pp.viper.model.interpreter;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;

public class InterpreterTest {
    /**
     * Tests the execution of the max/3 predicate from maths.pl.
     */
    @Test
    public void maxTest() throws {
        InterpreterTest.runQuery(
            "src/test/resources/maths.pl",
            "max(42, 17, X).",
            Arrays.asList(
                new HashSet<>(Arrays.asList(new Substitution(new Variable("X"), new Number(42))))
            )
        );

        InterpreterTest.runQuery(
            "src/test/resources/maths.pl",
            "max(17, 42, X).",
            Arrays.asList(
                new HashSet<>(Arrays.asList(new Substitution(new Variable("X"), new Number(42))))
            )
        );
    }

    /**
     * Tests the execution of the sum/3 predicate from maths.pl.
     */
    @Test
    public void sumTest() throws {
        InterpreterTest.runQuery(
            "src/test/resources/maths.pl",
            "sum(25, X, 42).",
            Arrays.asList(
                new HashSet<>(Arrays.asList(new Substitution(new Variable("X"), new Number(17))))
            )
        );
    }

    /**
     * Tests the calculation of 10! from maths.pl.
     */
    @Test
    public void facultyTest() throws {
        InterpreterTest.runQuery(
            "src/test/resources/maths.pl",
            "fac(10, X).",
            Arrays.asList(
                new HashSet<>(Arrays.asList(new Substitution(new Variable("X"), new Number(3628800))))
            )
        );
    }

    /**
     * Tests the execution of the canonical "grandfather(Gramps, Grandchild)" query on simpsons.pl.
     */
    @Test
    public void simpsonsTest() throws {
        InterpreterTest.runQuery(
            "src/test/resources/simpsons_advanced.pl",
            "grandfather(Gramps, Grandchild).",
            Arrays.asList(
                new HashSet<>(Arrays.asList(
                    new Substitution(new Variable("Gramps"), Functor.atom("abe")),
                    new Substitution(new Variable("Grandchild"), Functor.atom("bart")))),
                new HashSet<>(Arrays.asList(
                    new Substitution(new Variable("Gramps"), Functor.atom("abe")),
                    new Substitution(new Variable("Grandchild"), Functor.atom("lisa"))))
            )
        );
    }

    /**
     * Tests the execution of a single cut.
     */
    @Test
    public void cutTest() throws {
        InterpreterTest.runQuery("src/test/resources/simpsons_advanced.pl", "!.", Arrays.asList(Collections.EMPTY_SET));
    }

    /**
     * Tests the execution of a single unification goal.
     */
    @Test
    public void unificationTest() throws {
        InterpreterTest.runQuery(
            "src/test/resources/simpsons_advanced.pl",
            "X = test.",
            Arrays.asList(new HashSet<>(Arrays.asList(new Substitution(new Variable("X"), Functor.atom("test")))))
        );
    }

    /**
     * Runs a query until completion and asserts that it produces the correct results.
     *
     * @param path path of the Prolog knowledge base
     * @param querySource the query as a string
     * @param expectedSolutions list of expected substitution sets
     */
    private static void runQuery(String path, String querySource, List<List<Substitution>> expectedSolutions) {
        try {
        String source = new String(Files.readAllBytes(Paths.get(path)));
        KnowledgeBase kb = new PrologParser(source).parse();
        Goal query = new PrologParser(querySource).parseGoalList().get(0);
        Interpreter interpreter = new Interpreter(kb, query);

        StepResult result = null;
        int solutionIndex = 0;

        while (result != StepResult.NO_MORE_SOLUTIONS) {
            result = interpreter.step();

            if (result == StepResult.SOLUTION_FOUND) {
                assertFalse("Found more solutions than expected!", solutionIndex >= expectedSolutions.size());

                Environment env = interpreter.getCurrent().get().getEnvironment();

                Set<Variable> toFind = query.getVariables();
                
                Set<Substitution> actualSolution = toFind.stream().map(variable -> {
                    return new Substitution(variable, env.applyAllSubstitutions(variable));
                }).collect(toSet());

                assertEquals(actualSolution, expectedSolutions.get(solutionIndex));

                solutionIndex++;
            }
        }
        
        assertFalse("Did not find enough solutions!", solutionIndex < expectedSolutions.size());
        } catch (IOException ioErr) {
            fail(String.format("IOException while running \"%s\" on %s: %s", querySource, path, ioErr.getMessage()));
        } catch (ParseException parseErr) {
            fail(String.format("ParseException while running \"%s\" on %s: %s",
                querySource, path, parseErr.getMessage()));
        }
    }
}
