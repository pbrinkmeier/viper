package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

// These tests rely heavily on the Number class; all tests for Number should be fulfilled first
public class AdditionOperationTest {
    private AdditionOperation fortyTwo;
    private AdditionOperation notFortyTwo;

    @Before
    public void init() {
        this.fortyTwo = new AdditionOperation(new Number(17), new Number(25));
        this.notFortyTwo = new AdditionOperation(new Number(17), new Number(24));
    }

    @Test
    public void calculateTest() throws TermEvaluationException {
        assertEquals(fortyTwo.evaluate(), new Number(42));
        assertNotEquals(notFortyTwo.evaluate(), new Number(42));
        assertEquals(notFortyTwo.evaluate(), new Number(41));
    }

    @Test
    public void createNewTest() {
        assertEquals(this.fortyTwo.createNew(Arrays.asList(new Number(1), new Number(2))),
                new AdditionOperation(new Number(1), new Number(2)));
    }
}
