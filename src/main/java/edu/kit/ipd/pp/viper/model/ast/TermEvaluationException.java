package edu.kit.ipd.pp.viper.model.ast;

public abstract class TermEvaluationException extends Exception {
    /**
     * @return The error message of the exception
     */
    public abstract String getMessage();
}
