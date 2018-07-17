package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.UnificationActivationRecord;

import java.util.List;
import java.util.Optional;

/**
 * An unification goal.
 * This represent a Prolog goal of the form A = B.
 */
public class UnificationGoal extends Goal {
    /**
     * Initializes an unification goal with its left and right hand side.
     *
     * @param lhs left hand side of the unification
     * @param rhs right hand side of the unification
     */
    public UnificationGoal(Term lhs, Term rhs) {
        // TODO
    }

    @Override
    public UnificationGoal transform(TermTransformationVisitor visitor) {
        // TODO
        return null;
    }

    @Override
    public List<Variable> getVariables() {
        // TODO
        return null;
    }

    @Override
    public UnificationActivationRecord createActivationRecord(
        Interpreter interpreter, 
        Optional<FunctorActivationRecord> parent
    ) {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        // TODO
        return null;
    }

    @Override
    public boolean equals(Object other) {
        // TODO
        return false;
    }
}
