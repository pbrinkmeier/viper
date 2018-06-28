package edu.kit.ipd.pp.viper.model.ast;

import org.junit.*;
import static org.junit.Assert.*;

public class NumberTest {
    private Number num;

    @Before
    public void init() {
        this.num = new Number(42);
    }

    @Test
    public void getNumberTest() {
        assertEquals(42, this.num.getNumber());
    }

    @Test
    public void toStringTest() {
        assertEquals("42", this.num.toString());
    }

    @Test
    public void toHtmlTest() {
        assertEquals("42", this.num.toString());
    }

    @Test
    public void evaluateTest() throws TermEvaluationException {
        Number evaluated = this.num.evaluate();

        assertEquals(this.num.getNumber(), evaluated.getNumber());
    }
}
