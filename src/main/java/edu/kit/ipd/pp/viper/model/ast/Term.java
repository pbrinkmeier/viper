package edu.kit.ipd.pp.viper.model.ast;

public abstract class Term {
    /**
     * Interface for TermVisitors.
     *
     * @param visitor visitor to visit this term
     * @return the result of the visit
     */
    public abstract <ResultType> ResultType accept(TermVisitor<ResultType> visitor);

    /**
     * Evaluates this term arithmetically.
     *
     * @return a number term containing the result of the evaluation
     * @throws TermEvaluationException when evaluation fails (for example when trying to evaluate a functor)
     */
    public abstract Number evaluate() throws TermEvaluationException;

    /**
     * @return a string representation of this term
     */
    @Override
    public abstract String toString();

    /**
     * @return a GraphViz-compatible HTML representation of this term
     */
    public abstract String toHtml();

    /**
     * Force subclasses to implement equals(). Note: this does explicitly not
     * override equals(Object), because calls to non-terms should always return
     * false.
     *
     * @return whether two terms are equal
     */
    @Override
    public abstract boolean equals(Object other);
}
