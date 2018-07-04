package edu.kit.ipd.pp.viper.model.parser;

/**
 * A token of the Prolog language.
 */
public class Token {
    /**
     * token type of this Token
     */
    private final TokenType type;
    /**
     * the text of this token in the source code
     */
    private final String text;
    private final int line;
    private final int col;

    /**
     * Constructs a token.
     * @param type the token type
     * @param text text of this token in the source code
     * @param line line this token is in
     * @param col column this token begins
     */
    public Token(TokenType type, String text, int line, int col) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.col = col;
    }

    /**
     * Returns the token type
     * @return token type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Returns the text of this token in the source code
     * @return text of this token in the source code
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the line this token is in
     * @return line this token is in
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns the column this token begins
     * @return column this token begins
     */
    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return type + "(\"" + text + "\")";
    }
}