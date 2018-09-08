package edu.kit.ipd.pp.viper.model.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.ast.Variable;

public class ParserTest {
    /**
     * Tests the successful parsing of the given prolog code.
     * 
     * @throws ParseException if the prolog code couldn't be parsed
     * @throws IOException if the file couldn't be read
     */
    @Test
    public void parseSimpsonsTest() throws ParseException, IOException {
        String src = new String(Files.readAllBytes(Paths.get("src/test/resources/simpsons.pl")));

        KnowledgeBase emptyBase = new KnowledgeBase(Arrays.asList());
        KnowledgeBase testBase = new KnowledgeBase(Arrays.asList(
            new Rule(new Functor("father", Arrays.asList(Functor.atom("abe"), Functor.atom("homer"))),
                    Arrays.asList()),
            new Rule(new Functor("father", Arrays.asList(Functor.atom("homer"), Functor.atom("bart"))),
                    Arrays.asList()),
            new Rule(new Functor("grandfather", Arrays.asList(new Variable("X"), new Variable("Y"))), Arrays.asList(
                    new FunctorGoal(new Functor("father", Arrays.asList(new Variable("X"), new Variable("Z")))),
                    new FunctorGoal(new Functor("father", Arrays.asList(new Variable("Z"), new Variable("Y")))))))
        );
        
        KnowledgeBase parsed = new PrologParser(src).parse();
        assertEquals(parsed, testBase);
        assertNotEquals(parsed,emptyBase);
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
    
    @Test
    public void tokenToStringTest() {
        for (final TokenType type : TokenType.values()) {
            final String text = "Test " + Integer.toString(type.ordinal());
            final int line = 0;
            final int col = 0;
            
            final String expected = type + "(\"" + text + "\")";
            assertEquals(expected, new Token(type, text, line, col).toString());
        }
    }
    
    @Test (expected = ParseException.class)
    public void illegalCharacterTest() throws ParseException {
        new PrologParser("@").parse();
    }
    
    @Test (expected = ParseException.class)
    public void simpleParseExceptionTest() throws ParseException {
        new PrologParser("test(").parse();
    }
    
    @Test (expected = ParseException.class)
    public void illegalMultiSimOperatorTest() throws ParseException {
        new PrologParser("5 =@= 2.").parse();
    }    
}
