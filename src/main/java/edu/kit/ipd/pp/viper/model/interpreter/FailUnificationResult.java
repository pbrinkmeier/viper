package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Term;

import java.util.List;

class FailUnificationResult extends UnificationResult {
    private final Term lhs;
    private final Term rhs;

    /**
     * Intializes a fail-result with a left and right hand side of a failed unification.
     *
     * @param lhs left hand side of the failed unification
     * @param rhs right hand side of the failed unification
     */
    public FailUnificationResult(Term lhs, Term rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public List<Substitution> getSubstitutions() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getErrorMessage() {
        return String.format("%s is not unifiable with %s", this.lhs, this.rhs);
    }

    @Override
    public String toHtml() {
        return String.format("%s is not unifiable with %s", this.lhs.toHtml(), this.rhs.toHtml());
    }
}
