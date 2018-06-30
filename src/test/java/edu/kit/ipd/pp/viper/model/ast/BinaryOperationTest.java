package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class BinaryOperationTest {
    private BinaryOperation plus;
    private BinaryOperation minus;
    private BinaryOperation times;

    @Before
    public void init() {
        this.plus = new AdditionOperation(new Number(40), new Number(2));
        this.minus = new SubtractionOperation(new Number(100), new Number(58));
        this.times = new MultiplicationOperation(new Number(6), new Number(7));
    }

    @Test
    public void toStringTest() {
        assertEquals("(40 + 2)", this.plus.toString());
        assertEquals("(100 - 58)", this.minus.toString());
        assertEquals("(6 * 7)", this.times.toString());

        assertEquals(
            "(6 * (4 + 3))",
            new MultiplicationOperation(new Number(6), new AdditionOperation(new Number(4), new Number(3))).toString()
        );

        assertEquals(
            "(A/1 + 1)",
            new AdditionOperation(new Variable("A", 1), new Number(1)).toString()
        );
    }

    @Test
    public void toHtmlTest() {
        assertEquals("(40 + 2)", this.plus.toHtml());
        assertEquals("(100 - 58)", this.minus.toHtml());
        assertEquals("(6 * 7)", this.times.toHtml());

        assertEquals(
            "(A<sub>1</sub> + 1)",
            new AdditionOperation(new Variable("A", 1), new Number(1)).toHtml()
        );
    }

    @Test
    public void equalsTest() {
        assertEquals(new AdditionOperation(new Number(40), new Number(2)), this.plus);
        assertNotEquals(new Functor("+", Arrays.asList(new Number(40), new Number(2))), this.plus);
        assertEquals(new SubtractionOperation(new Number(100), new Number(58)), this.minus);
        assertNotEquals(new Functor("-", Arrays.asList(new Number(100), new Number(58))), this.minus);
        assertEquals(new MultiplicationOperation(new Number(6), new Number(7)), this.times);
        assertNotEquals(new Functor("*", Arrays.asList(new Number(6), new Number(7))), this.times);
    }
}
