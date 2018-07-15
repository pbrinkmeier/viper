package edu.kit.ipd.pp.viper.model.parser;

/**
 * Exception, that can be thrown by the parser and the lexer if there are syntax
 * errors in the given program.
 */
public class ParseException extends Exception {
    private static final long serialVersionUID = -2751382378103275785L;

    /**
     * Initializes a new Exception with the given message by calling super(message).
     * 
     * @param message The error message for the new Exception
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Calls super.getMessage().
     * 
     * @return The message of this exception.
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
