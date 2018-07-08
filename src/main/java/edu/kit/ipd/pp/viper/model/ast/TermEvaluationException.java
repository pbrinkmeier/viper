package edu.kit.ipd.pp.viper.model.ast;

public abstract class TermEvaluationException extends Exception {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -8359672800462736356L;

    /**
     * @return The error message of the exception
     */
    public abstract String getMessage();
}
