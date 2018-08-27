package edu.kit.ipd.pp.viper.model.ast;

import org.junit.*;
import static org.junit.Assert.*;

import java.math.BigInteger;

public class NumberTest {
    private Number num;

    /**
     * Initializes the number used in the tests.
     */
    @Before
    public void init() {
        this.num = new Number(BigInteger.valueOf(42));
    }

    /**
     * Tests the Getter-Method for the actual number.
     */
    @Test
    public void getNumberTest() {
        assertEquals(BigInteger.valueOf(42), this.num.getNumber());
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
        assertNotEquals(this.num, null);
        assertNotEquals(this.num, new Object());

        // two number terms should be equal if the numbers they represent are equal
        assertEquals(BigInteger.valueOf(42), BigInteger.valueOf(42));
        assertEquals(BigInteger.valueOf(0), BigInteger.valueOf(0));
        BigInteger max = BigInteger.valueOf(Integer.MAX_VALUE);
        assertEquals(max.add(max), max.add(max));

        // two number should not be equal if the numbers they represent are not equal
        assertNotEquals(BigInteger.valueOf(42), BigInteger.valueOf(24));
        assertNotEquals(BigInteger.valueOf(69), BigInteger.valueOf(96));
        assertNotEquals(BigInteger.valueOf(12), BigInteger.valueOf(0));
    }
}
