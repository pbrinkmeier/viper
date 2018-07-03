package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.UnificationActivationRecord;

import java.util.Optional;

public class UnificationGoal extends Goal {
    /**
     * Initializes an UnificationGoal with a left and right hand side.
     *
     * @param lhs left hand side of the unification
     * @param rhs right hand side of the unification
     */
    public UnificationGoal(Term lhs, Term rhs) {
        // TODO
    }

    /**
     * Getter-method for the left hand side of the unification.
     *
     * @return left hand side of the unification
     */
    public Term getLhs() {
        // TODO
        return null;
    }

    /**
     * Getter-method for the right hand side of the unification.
     *
     * @return right hand side of the unification
     */
    public Term getRhs() {
        // TODO
        return null;
    }

    /**
     * Creates a new UnificationActivationRecord for this goal.
     *
     * @param parent optional parent ActivationRecord
     * @return new UnificationActivationRecord for this goal
     */
    @Override
    public UnificationActivationRecord createActivationRecord(Optional<FunctorActivationRecord> parent) {
        // TODO
        return null;
    }

    /**
     * Getter-method for a string representation of this goal.
     * This representation will be of the form "[lhs] = [rhs]".
     *
     * @return string representation of this goal
     */
    @Override
    public String toString() {
        // TODO
        return null;
    }
}
