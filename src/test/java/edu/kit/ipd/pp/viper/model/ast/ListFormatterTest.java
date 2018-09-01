package edu.kit.ipd.pp.viper.model.ast;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class ListFormatterTest {
    private Term validList;
    private Term emptyList;
    private Term notAList;
    private Term notEvenListy;
    private Term slightlyListy;

    @Before
    public void init() {
        this.validList =
            cons(new Number(1),
            cons(Functor.atom("two"),
            cons(new Variable("Three", 4),
            Functor.atom("[]"))));
        this.emptyList =
            Functor.atom("[]");
        this.slightlyListy =
            new Functor("cons", Arrays.asList(new Number(42), Functor.atom("nil")));
    }

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

    @Test
    public void asHtmlTest() {
        assertEquals(Optional.of("[1, two, Three&#8324;]"), ListFormatter.asHtml(this.validList));
    }

    // helper method for list creation
    private Term cons(Term car, Term cdr) {
        return new Functor("[|]", Arrays.asList(car, cdr));
    }
}
