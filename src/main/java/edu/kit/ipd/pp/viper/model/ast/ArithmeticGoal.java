package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.ArithmeticActivationRecord;

import java.util.Optional;

public class ArithmeticGoal extends Goal {
    /**
     * Initializes an ArithmeticGoal (is-goal) with a left and right hand side.
     *
     * @param lhs left hand side of the arithmetic equation
     * @param rhs right hand side of the arithmetic equation
     */
    public ArithmeticGoal(Term lhs, Term rhs) {
        // TODO
    }

    /**
     * Getter-method for the left hand side of the arithmetic equation.
     *
     * @return left hand side of the arithmetic equation
     */
    public Term getLhs() {
        // TODO
        return null;
    }

    /**
     * Getter-method for the right hand side of the arithmetic equation.
     *
     * @return right hand side of the arithmetic equation.
     */
    public Term getRhs() {
        // TODO
        return null;
    }

    /**
     * Creates a new ArithmeticActivationRecord for this goal
     *
     * @param parent optional parent ActivationRecord
     * @return new ArithmeticActivationRecord
     */
    @Override
    public ArithmeticActivationRecord createActivationRecord(Optional<FunctorActivationRecord> parent) {
        // TODO
        return null;
    }

    /**
     * Getter-method for a string representation of this goal.
     * This representation will be of the form "[lhs] is [rhs]".
     *
     * @return string representation of this goal
     */
    @Override
    public String toString() {
        // TODO
        return null;
    }
}
