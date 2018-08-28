package edu.kit.ipd.pp.viper.model.ast;

import org.junit.*;
import static org.junit.Assert.*;

public class VariableTest {
    private Variable withoutIndex;
    private Variable withIndex;

    /**
     * Initializes the variables used in the tests.
     */
    @Before
    public void init() {
        this.withoutIndex = new Variable("X");
        this.withIndex = new Variable("X", 42);
    }

    /**
     * Tests the conversion from a Variable to a String.
     */
    @Test
    public void toStringTest() {
        assertEquals("X", this.withoutIndex.toString());
        assertEquals("X_42", this.withIndex.toString());
    }

    /**
     * Tests the converrsion from a Variable to HTML code.
     */
    @Test
    public void toHtmlTest() {
        assertEquals("X", this.withoutIndex.toHtml());
        assertEquals("X&#8324;&#8322;", this.withIndex.toHtml());
    }

    /**
     * Tests the Getter-Method for the name of the variable.
     */
    @Test
    public void getNameTest() {
        assertEquals("X", this.withoutIndex.getName());
        assertEquals("X", this.withIndex.getName());
    }

    /**
     * Tests the Getter-Method for the index of the variable.
     */
    @Test
    public void getIndexTest() {
        assertTrue(!this.withoutIndex.getIndex().isPresent());
        assertEquals(42, this.withIndex.getIndex().get().intValue());
    }

    /**
     * Tests if unset variables throw an adequate exception.
     * 
     * @throws UnsetVariableException if the variable is not set
     */
    @Test(expected = UnsetVariableException.class)
    public void evaluateTest() throws UnsetVariableException {
        this.withoutIndex.evaluate();
    }

    /**
     * Tests the variable comparison.
     */
    @Test
    public void equalsTest() {
        assertNotEquals(this.withIndex, null);
        assertNotEquals(this.withIndex, new Object());

        assertEquals(new Variable("X"), this.withoutIndex);
        assertEquals(new Variable("X", 42), this.withIndex);

        assertNotEquals(this.withoutIndex, new Variable("Y"));
        assertNotEquals(this.withoutIndex, new Variable("X", 42));

        assertNotEquals(this.withIndex, new Variable("Y", 42));
        assertNotEquals(this.withIndex, new Variable("X", 100));
        assertNotEquals(this.withIndex, new Variable("X"));
        assertNotEquals(this.withIndex, new Variable("Y"));
    }
}
