package edu.kit.ipd.pp.viper.model.ast;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class ListFormatterTest {
    private Term validList;
    private Term emptyList;
    private Term slightlyListy;

    /**
     * Initializes the terms [1, two, Three_4], [] and cons(42, nil) before each test.
     */
    @Before
    public void init() {
        this.validList
            = this.cons(new Number(1),
            this.cons(Functor.atom("two"),
            this.cons(new Variable("Three", 4),
            Functor.atom("[]"))));
        this.emptyList
            = Functor.atom("[]");
        this.slightlyListy
            = new Functor("cons", Arrays.asList(new Number(42), Functor.atom("nil")));
    }

    /**
     * Tests the static asString method for printing on the console.
     */
    @Test
    public void asStringTest() {
        // A valid list should be printed as such
        assertEquals(Optional.of("[1, two, Three_4]"), ListFormatter.asString(this.validList));
        // An empty list ist also a valid list
        assertEquals(Optional.of("[]"), ListFormatter.asString(this.emptyList));
        // Any other kind of term should not be interpreted as a list
        assertEquals(Optional.empty(), ListFormatter.asString(this.slightlyListy));
        assertEquals(Optional.empty(), ListFormatter.asString(new Number(42)));
        assertEquals(Optional.empty(), ListFormatter.asString(new Variable("X")));
        assertEquals(Optional.empty(), ListFormatter.asString(Functor.atom("[|]")));
        assertEquals(Optional.empty(), ListFormatter.asString(new Functor("[]", Arrays.asList(Functor.atom("fake")))));
    }

    /**
     * Tests the static asHtml method for display in the visualisation.
     */
    @Test
    public void asHtmlTest() {
        assertEquals(Optional.of("[1, two, Three&#8324;]"), ListFormatter.asHtml(this.validList));
    }

    // helper method for list creation
    private static Term cons(Term car, Term cdr) {
        return new Functor("[|]", Arrays.asList(car, cdr));
    }
}
