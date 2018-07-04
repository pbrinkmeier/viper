package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.junit.*;
import static org.junit.Assert.*;

public class InterpreterTest {
    @Test public void singleStepTest() throws IOException, ParseException {
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
    }
}
