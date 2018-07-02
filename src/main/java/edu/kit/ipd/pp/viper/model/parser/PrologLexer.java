package edu.kit.ipd.pp.viper.model.parser;

/**
 * Splits the program into tokens (for example: keywords, arithmetic operations, variables, numbers, ...)
 * and assigns them to a certain TokenType.
 */
public class PrologLexer {
    private final String program;
    
    /**
     * Initializes a new lexer with the given program.
     * 
     * @param program The given program
     */
    public PrologLexer(String program) {
        this.program = program;
    }

    /**
     * Finds the next program token.
     * 
     * @return The Token for the next part of the program
     */
    public Token nextToken() {
        // TODO
        return null;
    }
}
