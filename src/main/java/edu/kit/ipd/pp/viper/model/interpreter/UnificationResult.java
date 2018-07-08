package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.Collections;
import java.util.List;

import edu.kit.ipd.pp.viper.model.ast.Term;

public final class UnificationResult {
    private final boolean success;
    private final List<Substitution> substitutions;
    private final Term lhs;
    private final Term rhs;

    private UnificationResult() throws Exception {
        throw new Exception();
    }

    private UnificationResult(List<Substitution> substitutions) {
        this.success = true;
        this.substitutions = Collections.unmodifiableList(substitutions);
        this.lhs = null;
        this.rhs = null;
    }

    private UnificationResult(Term lhs, Term rhs) {
        this.success = false;
        this.lhs = lhs;
        this.rhs = rhs;
        this.substitutions = null;
    }

    /**
     * Initializes a success-result with a list of substitutions.
     *
     * @param substitutions list of substitutions in this result
     * @return an UnificationResult object
     */
    public static UnificationResult success(List<Substitution> substitutions) {
        return new UnificationResult(substitutions);
    }

    /**
     * Initializes a fail-result with two terms that could not be unified.
     *
     * @param lhs left hand side of the failed unification
     * @param rhs right hand side of the failed unification
     * @return an UnificationResult object
     */
    public static UnificationResult fail(Term lhs, Term rhs) {
        return new UnificationResult(lhs, rhs);
    }

    /**
     * Whether this result is a success-result.
     *
     * @return whether this result is a success-result
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * Getter-method for the substitutions of a success-result.
     *
     * @return list of substitutions in this result (immutable)
     * @throws UnsupportedOperationException when trying to get the substitutions of a fail-result
     */
    public List<Substitution> getSubstitutions() throws UnsupportedOperationException {
        if (!this.isSuccess()) {
            throw new UnsupportedOperationException();
        }

        return this.substitutions;
    }

    /**
     * Getter-method for the error-message of a fail-result.
     *
     * @return error message describing how the unification went wrong
     * @throws UnsupportedOperationException when trying to get the error message of a success-result
     */
    public String getErrorMessage() throws UnsupportedOperationException {
        if (this.isSuccess()) {
            throw new UnsupportedOperationException();
        }

        return String.format("%s ≠ %s", this.lhs.toString(), this.rhs.toString());
    }

    /**
     * Getter-method for a GraphViz-compatible HTML representation of this result.
     *
     * @return HTML representation of this result
     */
    public String toHtml() {
        if (this.isSuccess()) {
            List<Substitution> subst = this.getSubstitutions();
            String repr = "";

            for (int index = 0; index < subst.size(); index++) {
                repr +=
                    String.format("%s ⇒ %s",
                        subst.get(index).getReplace().toHtml(),
                        subst.get(index).getBy().toHtml()
                    );

                if (index != subst.size() - 1) {
                    repr += ", ";
                }
            }

            return repr;
        } else {
            return String.format("%s ≠ %s", this.lhs.toHtml(), this.rhs.toHtml());
        }
    }
}
