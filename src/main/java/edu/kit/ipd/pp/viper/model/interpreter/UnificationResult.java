package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.List;

import edu.kit.ipd.pp.viper.model.ast.Term;

public class UnificationResult {
    /**
     * @param substitutions
     * @return
     */
    public static UnificationResult success(List<Substitution> substitutions) {
        // TODO
        return null;
    }

    /**
     * @param lhs
     * @param rhs
     * @return
     */
    public static UnificationResult fail(Term lhs, Term rhs) {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public boolean isSuccess() {
        // TODO
        return false;
    }

    /**
     * @return
     */
    public List<Substitution> getSubstitutions() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public String getErrorMessage() {
        // TODO
        return "";
    }

    /**
     * @return
     */
    public String toHtml() {
        // TODO
        return "";
    }
}
