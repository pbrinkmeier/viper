package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class BinaryOperationTest {
    private BinaryOperation plus;
    private BinaryOperation minus;
    private BinaryOperation times;

    /**
     * Initializes the binary operations used in the tests.
     */
    @Before
    public void init() {
        this.plus = new AdditionOperation(new Number(40), new Number(2));
        this.minus = new SubtractionOperation(new Number(100), new Number(58));
        this.times = new MultiplicationOperation(new Number(6), new Number(7));
    }

    /**
     * Tests the conversion from a binary operation to a string.
     */
    @Test
    public void toStringTest() {
        assertEquals("(40 + 2)", this.plus.toString());
        assertEquals("(100 - 58)", this.minus.toString());
        assertEquals("(6 * 7)", this.times.toString());

        assertEquals("(6 * (4 + 3))",
                new MultiplicationOperation(new Number(6), new AdditionOperation(new Number(4), new Number(3)))
                        .toString());

        assertEquals("(A_1 + 1)", new AdditionOperation(new Variable("A", 1), new Number(1)).toString());
    }

    /**
     * Tests the conversion from a binary operation to HTML code.
     */
    @Test
    public void toHtmlTest() {
        assertEquals("(40 + 2)", this.plus.toHtml());
        assertEquals("(100 - 58)", this.minus.toHtml());
        assertEquals("(6 * 7)", this.times.toHtml());

        assertEquals("(A&#8321; + 1)", new AdditionOperation(new Variable("A", 1), new Number(1)).toHtml());
    }

    /**
     * Tests the comparison of binary operations.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.plus, null);
        assertNotEquals(this.plus, new Object());
        // two binops shouldn't be equal if their operands differ
        assertNotEquals(new AdditionOperation(new Number(41), new Number(2)), this.plus);
        assertNotEquals(new AdditionOperation(new Number(40), new Number(3)), this.plus);

        assertEquals(new AdditionOperation(new Number(40), new Number(2)), this.plus);
        assertNotEquals(new Functor("+", Arrays.asList(new Number(40), new Number(2))), this.plus);
        assertEquals(new SubtractionOperation(new Number(100), new Number(58)), this.minus);
        assertNotEquals(new Functor("-", Arrays.asList(new Number(100), new Number(58))), this.minus);
        assertEquals(new MultiplicationOperation(new Number(6), new Number(7)), this.times);
        assertNotEquals(new Functor("*", Arrays.asList(new Number(6), new Number(7))), this.times);
    }

    /**
     * Tests the Getter-Methods for the left-hand side of the operation.
     */
    @Test
    public void getLhsTest() {
        assertEquals(new Number(40), this.plus.getLhs());
        assertEquals(new Number(100), this.minus.getLhs());
        assertEquals(new Number(6), this.times.getLhs());
    }

    /**
     * Tests the Getter-Methods for the right-hand side of the operation.
     */
    @Test
    public void getRhsTest() {
        assertEquals(new Number(2), this.plus.getRhs());
        assertEquals(new Number(58), this.minus.getRhs());
        assertEquals(new Number(7), this.times.getRhs());
    }
}
