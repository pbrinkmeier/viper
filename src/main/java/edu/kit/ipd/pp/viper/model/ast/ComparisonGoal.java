package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;

public abstract class ComparisonGoal extends Goal {
    /**
     * @param lhs
     * @param rhs
     */
    public ComparisonGoal(Term lhs, Term rhs) {
        // TODO
    }

    /**
     * @return
     */
    public Term getLhs() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public Term getRhs() {
        // TODO
        return null;
    }

    /**
     * @param lhs
     * @param rhs
     * @return
     */
    public abstract boolean compareNumbers(int lhs, int rhs);

    /**
     * @param parent
     * @return
     */
    public ActivationRecord createActivationRecord(Optional<ActivationRecord> parent) {
        // TODO
        return null;
    }
}
