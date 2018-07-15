package edu.kit.ipd.pp.viper.model.ast;

import org.junit.*;
import static org.junit.Assert.*;

public class NumberTest {
    private Number num;

    /**
     * Initializes the number used in the tests.
     */
    @Before
    public void init() {
        this.num = new Number(42);
    }

    /**
     * Tests the Getter-Method for the actual number.
     */
    @Test
    public void getNumberTest() {
        assertEquals(42, this.num.getNumber());
    }

    /**
     * Tests the conversion from a Number to a String.
     */
    @Test
    public void toStringTest() {
        assertEquals("42", this.num.toString());
    }

    /**
     * Tests the conversion from a Number to HTML code.
     */
    @Test
    public void toHtmlTest() {
        assertEquals("42", this.num.toString());
    }

    /**
     * Tests whether the evaluation of a number properly returns the number itself.
     */
    @Test
    public void evaluateTest() {
        Number evaluated = this.num.evaluate();

        assertEquals(this.num.getNumber(), evaluated.getNumber());
    }

    /**
     * Tests number comparison.
     */
    @Test
    public void equalsTest() {
        // two number terms should be equal if the numbers they represent are equal
        assertEquals(new Number(42), new Number(42));
        assertEquals(new Number(0), new Number(0));
        assertEquals(new Number(Integer.MAX_VALUE), new Number(Integer.MAX_VALUE));

        // two number should not be equal if the numbers they represent are not equal
        assertNotEquals(new Number(42), new Number(24));
        assertNotEquals(new Number(69), new Number(96));
        assertNotEquals(new Number(12), new Number(0));
    }
}
