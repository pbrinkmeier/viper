package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;

import java.util.Arrays;
import java.util.List;

/**
 * A unifier tries to unify a term with another term.
 */
public abstract class Unifier implements TermVisitor<UnificationResult> {
    /**
     * Getter-method for the term this unififer will try to unify with.
     *
     * @return term this unififer will try to unify with
     */
    protected abstract Term getTerm();

    // ---

    /**
     * Creates an UnificationResult with a single substitution in it. Checks whether
     * the variable occurs in the term it should be substituted with and fails if it
     * does. This is a helper method meant to be used in VariableUnifier, but also
     * in {@link #visit(Variable)}.
     *
     * @param replace variable to substitute
     * @param by term to substitute with
     * @return an UnificationResult according to the rules stated above
     */
    protected UnificationResult createSubstitution(Variable replace, Term by) {
        // do not add substitutions of variables with themselves
        if (replace.equals(by)) {
            return UnificationResult.success(Arrays.asList());
        }

        List<Variable> occurring = by.accept(new VariableExtractor());

        // avoid recursion
        if (occurring.contains(replace)) {
            return UnificationResult.fail(replace, by);
        }

        return UnificationResult.success(Arrays.asList(new Substitution(replace, by)));
    }

    /**
     * Implements the general case of unification with a functor. In the general
     * case, unification should fail here; this may be overwritten in *Unifiers
     * which do support Unification with Functors (e.g. FunctorUnifier,
     * VariableUnifier, but not NumberUnifier).
     *
     * @param functor functor to unify with
     * @return a fail-result
     */
    @Override
    public UnificationResult visit(Functor functor) {
        return UnificationResult.fail(this.getTerm(), functor);
    }

    /**
     * Implements the general case of unification with a variable. That is, to
     * return a success-result with a single substitution.
     *
     * @param variable variable to create a substitution for
     * @return a success-result containing a single substitution
     */
    @Override
    public UnificationResult visit(Variable variable) {
        return this.createSubstitution(variable, this.getTerm());
    }

    /**
     * Implements the general case of unification with a number. In the general
     * case, unification should fail here; this may overwritten in *Unifiers which
     * do support unification with Numbers (e.g. NumberUnifier, VariableUnifier, but
     * not FunctorUnifier).
     *
     * @param number number to unify with
     * @return a fail-result
     */
    @Override
    public UnificationResult visit(Number number) {
        return UnificationResult.fail(this.getTerm(), number);
    }
}
