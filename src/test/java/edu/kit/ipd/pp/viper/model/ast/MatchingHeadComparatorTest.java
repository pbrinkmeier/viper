package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class MatchingHeadComparatorTest {
    private Rule a;
    private Rule b;
    private Rule aParam;
    private MatchingHeadComparator comp;

    /**
     * Initializes a comparator and the following rules before each test:
     * "testA.", "testB." and "testA(X).".
     */
    @Before
    public void init() {
        this.a = new Rule(Functor.atom("testA"), Arrays.asList());
        this.b = new Rule(Functor.atom("testB"), Arrays.asList());
        this.aParam = new Rule(new Functor("testA", Arrays.asList(new Variable("X"))), Arrays.asList());
        this.comp = new MatchingHeadComparator();
    }

    /**
     * Tests the comparison of two rules by their heads.
     */
    @Test
    public void compareTest() {
        assertTrue("testA. < testB.", this.comp.compare(this.a, this.b) < 0);
        assertTrue("testA. < testA(X).", this.comp.compare(this.a, this.aParam) < 0);
    }
}
