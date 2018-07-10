package edu.kit.ipd.pp.viper.model.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;

public class InterpreterTest {
    @Test public void stepTest() throws IOException, ParseException {
        String source = new String(Files.readAllBytes(Paths.get("src/test/resources/simpsons.pl")));
        KnowledgeBase kb = new PrologParser(source).parse();
        Goal query = new PrologParser("grandfather(X, Y).").parseGoalList().get(0);
        Interpreter interpreter = new Interpreter(kb, query);
        
        assertEquals(query, interpreter.getQuery().getGoal());
        // current step should be empty since no step has been done yet
        assertEquals(Optional.empty(), interpreter.getCurrent());
        assertEquals(query, interpreter.getNext().get().getGoal());

        assertEquals(StepResult.STEPS_REMAINING, interpreter.step());

        assertEquals(query, interpreter.getCurrent().get().getGoal());
        FunctorActivationRecord subgoal = (FunctorActivationRecord) interpreter.getNext().get();
        assertEquals(
            new Functor("father", Arrays.asList(new Variable("X", 1), new Variable("Z", 1))),
            subgoal.getGoal().getFunctor()
        );
        assertEquals(new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z", 1))), subgoal.getFunctor());

        List<Functor> expectedFunctors = Arrays.asList(
            new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z", 1))),
            // second subgoal of gf(X, Y) is tried, fails on father(abe, homer)...
            new Functor("father", Arrays.asList(Functor.atom("homer"), new Variable("Y"))),
            // ... is thus tried again, succeeds on father(homer, bart)...
            new Functor("father", Arrays.asList(Functor.atom("homer"), new Variable("Y"))),
            // ... is then tried again, fails because there are no more matching rules.
            new Functor("father", Arrays.asList(Functor.atom("homer"), new Variable("Y"))),
            // backtrack to previous subgoal which then succeeds on father(homer, bart)...
            new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z", 1))),
            // making this query fail thrice (2x for non-matching functors, 1x because there are not more)
            new Functor("father", Arrays.asList(Functor.atom("bart"), new Variable("Y"))),
            new Functor("father", Arrays.asList(Functor.atom("bart"), new Variable("Y"))),
            new Functor("father", Arrays.asList(Functor.atom("bart"), new Variable("Y"))),
            // backtracks to first subgoal which fails because there are no more matching rules
            new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z", 1))),
            // backtracks to gf(X, Y)
            new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y")))
        );

        List<StepResult> expectedResults = Arrays.asList(
            StepResult.STEPS_REMAINING,
            StepResult.STEPS_REMAINING,
            StepResult.SOLUTION_FOUND,
            StepResult.STEPS_REMAINING,
            StepResult.STEPS_REMAINING,
            StepResult.STEPS_REMAINING,
            StepResult.STEPS_REMAINING,
            StepResult.STEPS_REMAINING,
            StepResult.STEPS_REMAINING,
            StepResult.NO_MORE_SOLUTIONS
        );

        assertEquals(expectedFunctors.size(), expectedResults.size());

        for (int i = 0; i < expectedFunctors.size(); i++) {
            assertEquals(expectedResults.get(i), interpreter.step());

            assertEquals(
                expectedFunctors.get(i),
                ((FunctorActivationRecord) interpreter.getCurrent().get()).getFunctor()
            );
        }

        assertEquals(StepResult.NO_MORE_SOLUTIONS, interpreter.step());
        assertTrue(!interpreter.getNext().isPresent());
    }
}
