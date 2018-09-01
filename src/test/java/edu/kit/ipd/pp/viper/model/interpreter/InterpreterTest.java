package edu.kit.ipd.pp.viper.model.interpreter;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;

public class InterpreterTest {
    @Test
    public void maxTest() throws IOException, ParseException {
        this.runQuery(
            "src/test/resources/maths.pl",
            "max(42, 17, X).",
            Arrays.asList(
                Arrays.asList(new Substitution(new Variable("X"), new Number(42)))
            )
        );

        this.runQuery(
            "src/test/resources/maths.pl",
            "max(17, 42, X).",
            Arrays.asList(
                Arrays.asList(new Substitution(new Variable("X"), new Number(42)))
            )
        );
    }

    @Test
    public void sumTest() throws IOException, ParseException {
        this.runQuery(
            "src/test/resources/maths.pl",
            "sum(25, X, 42).",
            Arrays.asList(
                Arrays.asList(new Substitution(new Variable("X"), new Number(17)))
            )
        );
    }

    @Test
    public void facultyTest() throws IOException, ParseException {
        this.runQuery(
            "src/test/resources/maths.pl",
            "fac(10, X).",
            Arrays.asList(
                Arrays.asList(new Substitution(new Variable("X"), new Number(3628800)))
            )
        );
    }

    @Test
    public void simpsonsTest() throws IOException, ParseException {
        this.runQuery(
            "src/test/resources/simpsons_advanced.pl",
            "grandfather(Gramps, Grandchild).",
            Arrays.asList(
                Arrays.asList(new Substitution(new Variable("Gramps"), Functor.atom("abe")), new Substitution(new Variable("Grandchild"), Functor.atom("bart"))),
                Arrays.asList(new Substitution(new Variable("Gramps"), Functor.atom("abe")), new Substitution(new Variable("Grandchild"), Functor.atom("lisa")))
            )
        );
    }

    @Test
    public void cutTest() throws IOException, ParseException {
        this.runQuery("src/test/resources/simpsons_advanced.pl", "!.", Arrays.asList(Arrays.asList()));
    }

    @Test
    public void unificationTest() throws IOException, ParseException {
        this.runQuery(
            "src/test/resources/simpsons_advanced.pl",
            "X = test.",
            Arrays.asList(Arrays.asList(new Substitution(new Variable("X"), Functor.atom("test"))))
        );
    }

    // helper method
    private void runQuery(String path, String querySource, List<List<Substitution>> expectedSolutions) throws IOException, ParseException {
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
                List<Variable> toFind = query.getVariables();
                
                List<Substitution> actualSolution = toFind.stream().map(variable -> {
                    return new Substitution(variable, env.applyAllSubstitutions(variable));
                }).collect(toList());

                assertEquals(actualSolution, expectedSolutions.get(solutionIndex));

                solutionIndex++;
            }
        }
        
        assertFalse("Did not find enough solutions!", solutionIndex < expectedSolutions.size());
    }
}
