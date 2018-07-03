package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.List;

import edu.kit.ipd.pp.viper.model.ast.Term;

public class UnificationResult {
    /**
     * Initializes a success-result with a list of substitutions.
     *
     * @param substitutions list of substitutions in this result
     * @return an UnificationResult object
     */
    public static UnificationResult success(List<Substitution> substitutions) {
        // TODO
        return null;
    }

    /**
     * Initializes a fail-result with two terms that could not be unified.
     *
     * @param lhs left hand side of the failed unification
     * @param rhs right hand side of the failed unification
     * @return an UnificationResult object
     */
    public static UnificationResult fail(Term lhs, Term rhs) {
        // TODO
        return null;
    }

    /**
     * Whether this result is a success-result.
     *
     * @return whether this result is a success-result
     */
    public boolean isSuccess() {
        // TODO
        return false;
    }

    /**
     * Getter-method for the substitutions of a success-result.
     *
     * @return list of substitutions in this result (immutable)
     * @throws UnsupportedOperationException when trying to get the substitutions of a fail-result
     */
    public List<Substitution> getSubstitutions() throws UnsupportedOperationException {
        // TODO
        return null;
    }

    /**
     * Getter-method for the error-message of a fail-result.
     *
     * @return error message describing how the unification went wrong
     * @throws UnsupportedOperationException when trying to get the error message of a success-result
     */
    public String getErrorMessage() throws UnsupportedOperationException {
        // TODO
        return "";
    }

    /**
     * Getter-method for a GraphViz-compatible HTML representation of this result.
     *
     * @return HTML representation of this result
     */
    public String toHtml() {
        // TODO
        return "";
    }
}
