package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Term;

import java.util.List;

/**
 * Result of an unification.
 * There are three kinds of unification results:
 *
 * - success is a successful unification that yielded a list of substitutions
 * - fail is a failed unification
 * - error is an error that prevented the unification to be tried in the first place
 */
public abstract class UnificationResult {
    /**
     * Initializes a success-result with a list of substitutions.
     *
     * @param substitutions list of substitutions in this result
     * @return an UnificationResult object
     */
    public static SuccessUnificationResult success(List<Substitution> substitutions) {
        return new SuccessUnificationResult(substitutions);
    }

    /**
     * Initializes a fail-result with two terms that could not be unified.
     *
     * @param lhs left hand side of the failed unification
     * @param rhs right hand side of the failed unification
     * @return an UnificationResult object
     */
    public static FailUnificationResult fail(Term lhs, Term rhs) {
        return new FailUnificationResult(lhs, rhs);
    }

    /**
     * Initializes an error-result with an error message.
     *
     * @param message error message describing what went wrong
     * @return an UnificationResult object
     */
    public static ErrorUnificationResult error(String message) {
        return new ErrorUnificationResult(message);
    }

    /**
     * Whether this result is a success-result.
     *
     * @return whether this result is a success-result
     */
    public abstract boolean isSuccess();

    /**
     * Whether this result is an error-result.
     *
     * @return whether this result is an error-result
     */
    public boolean isError() {
        return false;
    }

    /**
     * Getter-method for the substitutions of a success-result.
     *
     * @return list of substitutions in this result (immutable)
     * @throws UnsupportedOperationException when trying to get the substitutions of
     *             a fail-result
     */
    public abstract List<Substitution> getSubstitutions() throws UnsupportedOperationException;

    /**
     * Getter-method for the error-message of a fail-result.
     *
     * @return error message describing how the unification went wrong
     * @throws UnsupportedOperationException when trying to get the error message of
     *             a success-result
     */
    public abstract String getErrorMessage() throws UnsupportedOperationException;

    /**
     * Getter-method for a GraphViz-compatible HTML representation of this result.
     *
     * @return HTML representation of this result
     */
    public abstract String toHtml();
}
