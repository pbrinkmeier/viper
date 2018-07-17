package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.UnificationGoal;

import java.util.Optional;

/**
 * Execution state of an unification goal.
 * If an unification record has not been visited, it is not fulfilled.
 * If an unification record has been visited, but unification was unsuccessful, it is not fulfilled.
 * If an unification record has been visited and unification was successful, it is fulfilled.
 */
public class UnificationActivationRecord extends ActivationRecord {
    private final Term lhs;
    private final Term rhs;

    /**
     * Initializes an unification activation record.
     *
     * @param lhs left hand side of the unification
     * @param rhs right hand side of the unification
     * @param interpreter interpreter this AR belongs to
     * @param parent optional parent functor AR
     */
    public UnificationActivationRecord(
        Term lhs,
        Term rhs,
        Interpreter interpreter,
        Optional<FunctorActivationRecord> parent
    ) {
        super(null, null);
        this.lhs = null;
        this.rhs = null;
        // TODO
    }

    @Override
    public UnificationGoal getGoal() {
        // TODO
        return null;
    }

    @Override
    public <T> T accept(ActivationRecordVisitor<T> visitor) {
        // TODO
        return null;
    }

    @Override
    public Optional<ActivationRecord> step() {
        // TODO
        return null;
    }

    @Override
    public boolean isFulfilled() {
        // TODO
        return false;
    }
}
