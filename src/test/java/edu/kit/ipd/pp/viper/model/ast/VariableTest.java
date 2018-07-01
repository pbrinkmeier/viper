package edu.kit.ipd.pp.viper.model.ast;

import org.junit.*;
import static org.junit.Assert.*;

public class VariableTest {
    private Variable withoutIndex;
    private Variable withIndex;
    
    @Before
    public void init() {
        this.withoutIndex = new Variable("X");
        this.withIndex = new Variable("X", 42);
    }

    @Test
    public void toStringTest() {
        assertEquals("X", this.withoutIndex.toString());
        assertEquals("X/42", this.withIndex.toString());
    }

    @Test
    public void toHtmlTest() {
        assertEquals("X", this.withoutIndex.toHtml());
        assertEquals("X<sub>42</sub>", this.withIndex.toHtml());
    }

    @Test
    public void getNameTest() {
        assertEquals("X", this.withoutIndex.getName());
        assertEquals("X", this.withIndex.getName());
    }

    @Test
    public void getIndexTest() {
        assertTrue(!this.withoutIndex.getIndex().isPresent());
        assertEquals(42, this.withIndex.getIndex().get().intValue());
    }

    @Test(expected = UnsetVariableException.class)
    public void evaluateTest() throws UnsetVariableException {
        this.withoutIndex.evaluate();
    }

    @Test
    public void equalsTest() {
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
