package edu.kit.ipd.pp.viper.model.parser;

/**
 * Enumeration of all possible types of token a prolog program can contain
 */
public enum TokenType {
    /**
     * The separator of a rule head and it's goals
     */
    COLON_MINUS(":-"),

    /**
     * An equals sign (=)
     */
    EQ("="),

    /**
     * A less than sign (&lt;)
     */
    LESS("<"),

    /**
     * A less than and equal sign (=&lt;)
     */
    EQ_LESS("=<"),

    /**
     * A greater than sign (&gt;)
     */
    GREATER(">"),

    /**
     * A greater than and equal sign (&gt;=)
     */
    GREATER_EQ(">="),

    /**
     * Arithmetic equality (=:=)
     */
    EQ_COLON_EQ("=:="),

    /**
     * Not equal (=\=)
     */
    EQ_BS_EQ("=\\="),

    /**
     * End of File
     */
    EOF("End of Input"),

    /**
     * End of rule
     */
    DOT("."),

    /**
     * Separates two goals of a rule
     */
    COMMA(","),

    /**
     * Left parentheses
     */
    LP("("),

    /**
     * Right parentheses
     */
    RP(")"),

    /**
     * Left bracket
     */
    LB("["),

    /**
     * Right bracket
     */
    RB("]"),

    /**
     * Pipe sign, that separates list entries (|)
     */
    BAR("|"),

    /**
     * Plus sign
     */
    PLUS("+"),

    /**
     * Minus sign
     */
    MINUS("-"),

    /**
     * Star sign (*)
     */
    STAR("*"),

    /**
     * Exclamation mark
     */
    EXCLAMATION("!"),

    /**
     * Arithmetically evaluates the right side and unifies it with the left side
     */
    IS("is"),

    /**
     * A variable (can be anything that starts with an upper case letter)
     */
    VARIABLE("Variable"),

    /**
     * An identifier (can be anything that starts with a lower case letter)
     */
    IDENTIFIER("Identifier"),

    /**
     * A regular number
     */
    NUMBER("Number");

    private String value;

    private TokenType(String value) {
        this.value = value;
    }

    /**
     * Getter-Method for the string value of an enum.
     * 
     * @return string value of a specific enum
     */
    public String getString() {
        return this.value;
    }
}
