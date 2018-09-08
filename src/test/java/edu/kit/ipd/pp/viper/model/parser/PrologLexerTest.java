package edu.kit.ipd.pp.viper.model.parser;

import org.junit.*;
import static org.junit.Assert.*;

public class PrologLexerTest {
    /**
     * Makes sure that an operator consisting of multiple symbols at the end of a program does not result in an error.
     *
     * @throws ParseException when a parser error occurs
     */
    @Test
    public void handleMultiSimOperatorsTest() throws ParseException {
        assertEquals(TokenType.EQ_COLON_EQ, new PrologLexer("=:=").nextToken().getType());
    }

    /**
     * Makes sure that an identifier or number at the end of a program don't result in an error.
     *
     * @throws ParseException when a parser error occurs
     */
    @Test
    public void handleDefaultTest() throws ParseException {
        assertEquals(TokenType.IDENTIFIER, new PrologLexer("test").nextToken().getType());
        assertEquals(TokenType.NUMBER, new PrologLexer("42").nextToken().getType());
    }
}
