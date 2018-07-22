package edu.kit.ipd.pp.viper.model.parser;

import org.junit.*;

import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ParserTest {
    private PrologParser kbParser;
    private KnowledgeBase testBase;
    private KnowledgeBase emptyBase;

    /**
     * Initializes the KnowledgeBases and Parser used in the tests.
     * 
     * @throws IOException if the example program couldn't be loaded
     * @throws ParseException if the example program couldn't be parsed
     */
    @Before
    public void init() throws IOException, ParseException {
        String src = new String(Files.readAllBytes(Paths.get("src/test/resources/simpsons.pl")));
        this.kbParser = new PrologParser(src);

        this.testBase = new KnowledgeBase(Arrays.asList(
                new Rule(new Functor("father", Arrays.asList(Functor.atom("abe"), Functor.atom("homer"))),
                        Arrays.asList()),
                new Rule(new Functor("father", Arrays.asList(Functor.atom("homer"), Functor.atom("bart"))),
                        Arrays.asList()),
                new Rule(new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y"))), Arrays.asList(
                        new FunctorGoal(new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z")))),
                        new FunctorGoal(new Functor("father", Arrays.asList(new Variable("Z"), new Variable("Y"))))))));

        this.emptyBase = new KnowledgeBase(Arrays.asList());
    }

    /**
     * Tests the successful parsing of the given prolog code.
     * 
     * @throws ParseException if the prolog code couldn't be parsed
     */
    @Test
    public void parseTest() throws ParseException {
        KnowledgeBase parsed = this.kbParser.parse();
        assertEquals(parsed, this.testBase);
        assertNotEquals(parsed, this.emptyBase);
    }

    /**
     * Tests parsing the goal list of a given query.
     * 
     * @throws ParseException if the query couldn't be parsed
     */
    @Test
    public void parseGoalListTest() throws ParseException {
        List<Goal> parsedOneRule = new PrologParser("grandfather(X, Y).").parseGoalList();
        assertEquals(new FunctorGoal(new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y")))),
                parsedOneRule.get(0));
    }
}
