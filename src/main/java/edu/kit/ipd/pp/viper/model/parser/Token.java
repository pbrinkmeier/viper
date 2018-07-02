package edu.kit.ipd.pp.viper.model.parser;

/**
 * Represents a logical part of a prolog program. That includes symbols like separators, keywords, arithmetic operations
 * and even variables, identifiers and numbers.
 */
public class Token {
    private final TokenType type;
    private final String text;
    private final int line;
    private final int column;
    
    /**
     * Initializes a new Token with a specific token type, it's text, the line and the column.
     * 
     * @param type The specific TokenType that represents this Token
     * @param text The sign or multiple letters that this token contains
     * @param line The line where this Token can be found
     * @param column The position in the line
     */
    public Token(TokenType type, String text, int line, int column) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.column = column;
    }

    /**
     * Returns this tokens specific type.
     * 
     * @return This tokens type
     */
    public TokenType getType() {
        return this.type;
    }

    /**
     * Returns this tokens specific text.
     * 
     * @return This tokens text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the line this token can be found at.
     * 
     * @return This tokens line
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Returns this tokens position in the line.
     * 
     * @return This tokens column
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Returns a String representation of this token.
     * 
     * @return This token as a String.
     */
    @Override
    public String toString() {
        return this.type + "(\"" + text + "\")";
    }
}
