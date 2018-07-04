package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;

import java.util.Arrays;

public abstract class Unifier<R extends Term> implements TermVisitor<UnificationResult> {
    private final R term;

    /**
     * To be used by subclasses to set the term for error messages in methods implemented here.
     *
     * @param term term this unifier will try to unify with other terms
     */
    public Unifier(R term) {
        this.term = term;
    }

    /**
     * Getter-method for the term this unififer will try to unify with.
     *
     * @return term this unififer will try to unify with
     */
    protected R getTerm() {
        return this.term;
    }

    // ---

    /**
     * Implements the general case of unification with a functor.
     * In the general case, unification should fail here; this may be
     * overwritten in *Unifiers which do support Unification with Functors
     * (e.g. FunctorUnifier, VariableUnifier, but not NumberUnifier).
     *
     * @param functor functor to unify with
     * @return a fail-result
     */
    @Override
    public UnificationResult visit(Functor functor) {
        return UnificationResult.fail(this.getTerm(), functor);
    }

    /**
     * Implements the general case of unification with a variable.
     * That is, to return a success-result with a single substitution.
     *
     * @param variable variable to create a substitution for
     * @return a success-result containing a single substitution
     */
    @Override
    public UnificationResult visit(Variable variable) {
        return UnificationResult.success(Arrays.asList(new Substitution(variable, this.getTerm())));
    }

    /**
     * Implements the general case of unification with a number.
     * In the general case, unification should fail here; this may
     * overwritten in *Unifiers which do support unification with Numbers
     * (e.g. NumberUnifier, VariableUnifier, but not FunctorUnifier).
     *
     * @param number number to unify with
     * @return a fail-result
     */
    @Override
    public UnificationResult visit(Number number) {
        return UnificationResult.fail(this.getTerm(), number);
    }
}
