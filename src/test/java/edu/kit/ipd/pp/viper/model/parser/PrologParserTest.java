package edu.kit.ipd.pp.viper.model.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.AdditionOperation;
import edu.kit.ipd.pp.viper.model.ast.ArithmeticGoal;
import edu.kit.ipd.pp.viper.model.ast.CutGoal;
import edu.kit.ipd.pp.viper.model.ast.EqualGoal;
import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.GreaterThanGoal;
import edu.kit.ipd.pp.viper.model.ast.GreaterThanEqualGoal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.LessThanGoal;
import edu.kit.ipd.pp.viper.model.ast.LessThanEqualGoal;
import edu.kit.ipd.pp.viper.model.ast.MultiplicationOperation;
import edu.kit.ipd.pp.viper.model.ast.NotEqualGoal;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.ast.SubtractionOperation;
import edu.kit.ipd.pp.viper.model.ast.UnificationGoal;
import edu.kit.ipd.pp.viper.model.ast.Variable;

public class PrologParserTest {
    /**
     * Tests the successful parsing of the given prolog code.
     * The file parser.pl contains all valid constructs our subset of Prolog supports.
     * It is not meant to make sense.
     * 
     * @throws ParseException if the prolog code couldn't be parsed
     * @throws IOException if the file couldn't be read
     */
    @Test
    public void parseTest() throws ParseException, IOException {
        String src = new String(Files.readAllBytes(Paths.get("src/test/resources/parser.pl")));

        KnowledgeBase testBase = new KnowledgeBase(Arrays.asList(
            new Rule(Functor.atom("true"), Arrays.asList()),
            new Rule(new Functor("true", Arrays.asList(new Variable("X"))), Arrays.asList()),
            new Rule(new Functor("greaterThan", Arrays.asList(new Number(42), new Number(17))), Arrays.asList()),
            new Rule(Functor.atom("randomRule"), Arrays.asList(
                new FunctorGoal(new Functor("isValid", Arrays.asList(new Variable("Y")))),
                new CutGoal(),
                new UnificationGoal(Functor.atom("zomg"), new Variable("Msg")),
                new UnificationGoal(
                    new Functor("[|]", Arrays.asList(new Variable("X"),
                    new Functor("[|]", Arrays.asList(new Variable("Y"),
                    Functor.atom("[]"))))),
                    new Functor("[|]", Arrays.asList(new Number(1),
                    new Functor("[|]", Arrays.asList(new Number(2),
                    Functor.atom("[]")))))
                ),
                new ArithmeticGoal(
                    new Variable("X"),
                    new Number(42)
                ),
                new ArithmeticGoal(
                    new Variable("X"),
                    new SubtractionOperation(new AdditionOperation(new Number(42), new Number(30)), new Number(3))
                ),
                new ArithmeticGoal(
                    new Variable("X"),
                    new MultiplicationOperation(new Number(3), new AdditionOperation(new Number(10), new Number(13)))
                ),
                new LessThanGoal(new Variable("X"), new Number(100)),
                new LessThanEqualGoal(new Variable("X"), new Number(42)),
                new EqualGoal(new Variable("X"), new Number(42)),
                new NotEqualGoal(new Variable("X"), new Number(69)),
                new GreaterThanEqualGoal(new Variable("X"), new Number(17)),
                new GreaterThanGoal(new Variable("X"), new Number(17))
            )),
            new Rule(new Functor("listStuff", Arrays.asList(
                Functor.atom("[]"),
                new Functor("[|]", Arrays.asList(new Number(1),
                new Functor("[|]", Arrays.asList(new Number(2),
                Functor.atom("[]"))))),
                new Functor("[|]", Arrays.asList(new Number(1), new Number(2)))
            )), Arrays.asList())
        ));
        
        KnowledgeBase parsed = new PrologParser(src).parse();
        assertEquals(testBase, parsed);
    }

    @Test(expected = ParseException.class)
    public void illegalCharacterTest() throws ParseException {
        new PrologParser("@").parse();
    }
}
