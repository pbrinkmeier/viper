package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;

public class FunctorUnifier extends Unifier<Functor> {
    /**
     * Initializes a functor-unifier with the functor to try unification with.
     *
     * @param functor functor to try unification with
     */
    public FunctorUnifier(Functor functor) {
        super(functor);
    }

    /**
     * How to unify two functors.
     * If their names or arity are not equal, unification fails.
     * If they are, try to all their parameters.
     * If any of the unifications of parameters fails, unification fails.
     * For every unification of parameters, store the resulting substitutions.
     * If all unifications of parameters have succeeded, unification succeeds with the stored substitutions.
     *
     * @param functor functor to unify with
     * @return an UnificationResult according to the rules stated above
     */
    public UnificationResult visit(Functor functor) {
        return null;
    }
}
