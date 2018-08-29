package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*;

public class KnowledgeBaseTest {
    private List<Rule> rules;
    private KnowledgeBase kb;
    private KnowledgeBase incomplete;

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

    @Test
    public void toStringTest() {
        assertEquals("yes.\n\ngte(X, Y) :-\n  X > Y.\ngte(X, Y) :-\n  X =:= Y.\n", this.kb.toString());
    }

    @Test
    public void getMatchingRulesTest() {
        assertEquals(
            Arrays.asList(new Rule(Functor.atom("yes"), Arrays.asList())),
            this.kb.getMatchingRules(Functor.atom("yes"))
        );
    }

    @Test
    public void withRuleTest() {
        assertEquals(
            this.kb,
            this.incomplete.withRule(new Rule(new Functor("gte", Arrays.asList(new Variable("X"), new Variable("Y"))),
                Arrays.asList(new EqualGoal(new Variable("X"), new Variable("Y")))))
        );
    }

    // This is basically a dummy for the two edge cases, equals() has been tested above
    @Test
    public void equalsTest() {
        assertNotEquals(this.kb, null);
        assertNotEquals(this.kb, new Object());
    }

    @Test
    public void hashCodeTest() {
        assertEquals(Objects.hash(this.rules), this.kb.hashCode());
    }
}
