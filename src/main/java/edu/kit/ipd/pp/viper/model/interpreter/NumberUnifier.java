package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Number;

public class NumberUnifier extends Unifier<Number> {
    /**
     * Initializes the unifier with a number to do unification with
     * @param number number to unify
     */
    public NumberUnifier(Number number) {
        super(number);
    }

    /**
     * This method specifies how to unify two numbers.
     * If they are equal, the unification is successful, but
     * does not yield any subsitutions.
     * If they are not equal, the unification fails.
     *
     * @param number number to unify with
     * @return an UnificationResult according to the rules stated above
     */
    public UnificationResult visit(Number number) {
        return null;
    }
}
