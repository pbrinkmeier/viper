package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class KnowledgeBaseTest {
    private List<Rule> rules;
    private KnowledgeBase kb;
    private KnowledgeBase incomplete;

    /**
     * Initializes the two KnowledgeBases before each test.
     * The two kbs are:
     *
     * <pre>
     * yes.
     * gte(X, Y) :- X &gt; Y.
     * gte(X, Y) :- X =:= Y.
     * </pre>
     *
     * and
     *
     * <pre>
     * yes.
     * gte(X, Y) :- X &gt; Y.
     * </pre>
     */
    @Before
    public void init() {
        this.rules = Arrays.asList(
            new Rule(Functor.atom("yes"), Arrays.asList()),
            new Rule(new Functor("gte", Arrays.asList(new Variable("X"), new Variable("Y"))),
                Arrays.asList(new GreaterThanGoal(new Variable("X"), new Variable("Y")))),
            new Rule(new Functor("gte", Arrays.asList(new Variable("X"), new Variable("Y"))),
                Arrays.asList(new EqualGoal(new Variable("X"), new Variable("Y"))))
        );

        this.kb = new KnowledgeBase(this.rules);

        this.incomplete = new KnowledgeBase(Arrays.asList(
            new Rule(Functor.atom("yes"), Arrays.asList()),
            new Rule(new Functor("gte", Arrays.asList(new Variable("X"), new Variable("Y"))),
                Arrays.asList(new GreaterThanGoal(new Variable("X"), new Variable("Y"))))
        ));
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void toStringTest() {
        assertEquals("yes.\n\ngte(X, Y) :-\n  X > Y.\ngte(X, Y) :-\n  X =:= Y.\n", this.kb.toString());
    }

    /**
     * Tests the getMatchingRules method.
     */
    @Test
    public void getMatchingRulesTest() {
        assertEquals(
            Arrays.asList(new Rule(Functor.atom("yes"), Arrays.asList())),
            this.kb.getMatchingRules(Functor.atom("yes"))
        );
    }

    /**
     * Tests the getConflictingRules method.
     */
    @Test
    public void getConflictingRulesTest() {
        Rule yesRule = new Rule(Functor.atom("yes"), Arrays.asList());
        KnowledgeBase onlyYes = new KnowledgeBase(Arrays.asList(yesRule));
        assertEquals(
            new HashSet<>(Arrays.asList(yesRule)),
            this.kb.getConflictingRules(onlyYes)
        );
    }

    /**
     * Tests the withRule method that adds a single rule to a knowledge base.
     */
    @Test
    public void withRuleTest() {
        assertEquals(
            this.kb,
            this.incomplete.withRule(new Rule(new Functor("gte", Arrays.asList(new Variable("X"), new Variable("Y"))),
                Arrays.asList(new EqualGoal(new Variable("X"), new Variable("Y")))))
        );
    }

    /**
     * Tests the two edge cases for equals (namely comparing to null and an instance of a different class).
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.kb, null);
        assertNotEquals(this.kb, new Object());
    }

    /**
     * Tests the hashCode method.
     */
    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash(this.rules), this.kb.hashCode());
    }
}
