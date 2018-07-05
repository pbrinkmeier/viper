package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.interpreter.ComparisonActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;

public abstract class ComparisonGoal extends Goal {
    /**
     * Initializes a comparison goal with a left and right hand side.
     *
     * @param lhs left hand side of the comparison
     * @param rhs right hand side of the comparison
     */
    public ComparisonGoal(Term lhs, Term rhs) {
        // TODO
    }

    /**
     * Getter-method for the left hand side of the comparison.
     *
     * @return left hand side of the comparison
     */
    public Term getLhs() {
        // TODO
        return null;
    }

    /**
     * Getter-method for the right hand side of the comparison.
     *
     * @return right hand side of the comparison
     */
    public Term getRhs() {
        // TODO
        return null;
    }

    /**
     * Applies the comparison to two integers.
     * To be overwritten in the subgoals of this class.
     * This method needs parameters because the lhs and rhs of this object will not receive substitutions.
     *
     * @param lhs evaluated left hand side of the comparison
     * @param rhs evaluated right hand side of the comparison
     * @return whether the comparison was successful
     */
    public abstract boolean compareNumbers(int lhs, int rhs);

    /**
     * Creates an ActivationRecord for this comparison goal.
     *
     * @param parent optional parent activation record
     * @return new ComparisonActivationRecord corresponding to this goal
     */
    @Override
    public ComparisonActivationRecord createActivationRecord(Optional<FunctorActivationRecord> parent) {
        // TODO
        return null;
    }

    /**
     * Getter-method for a string representation of this comparison.
     * Uses the abstract method getSymbol().
     * The representation looks like this: "[lhs] [symbol] [rhs]"
     *
     * @return string representation of this comparison
     */
    @Override
    public String toString() {
        // TODO
        return null;
    }

    /**
     * Returns the symbol for this comparison.
     * Meant to be overwritten by the subclasses.
     *
     * @return symbol for this comparison
     */
    protected abstract String getSymbol();
    
    /**
     * Checks whether this equals another object. Will only return true for functors
     * of the same name and parameters.
     *
     * @param other other Object
     * @return whether this is equal to object according to the rules defined above
     */
    @Override
    public boolean equals(Object other) {
        // TODO
        return false;
    }
}
