package edu.kit.ipd.pp.viper.model.ast;

/**
 * An exception that may happen during arithmetic evaluation of a term.
 */
public abstract class TermEvaluationException extends Exception {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -8359672800462736356L;

    /**
     * Getter-method for the exception error message.
     *
     * @return error message of the exception
     */
    @Override
    public abstract String getMessage();
}
