package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.ComparisonGoal;
import edu.kit.ipd.pp.viper.model.ast.Goal;

import java.util.Optional;

/**
 * Represents an arithmetic comparison goal during execution.
 * If a comparison AR has not been visited, it is not fulfilled.
 * If a comparison AR has been visited, it is fulfilled if the comparison holds.
 */
public class ComparisonActivationRecord extends ActivationRecord {
    private final ComparisonGoal goal;

    /**
     * Initializes a comparison AR with an interpreter a parent, and a comparison goal.
     *
     * @param interpreter interpreter instance this AR belongs to
     * @param parent optional parent AR
     * @param goal comparison goal of this activation record
     */
    public ComparisonActivationRecord(
        Interpreter interpreter,
        Optional<FunctorActivationRecord> parent,
        ComparisonGoal goal
    ) {
        super(interpreter, parent);
        this.goal = goal;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    @Override
    public <T> T accept(ActivationRecordVisitor<T> visitor) {
        return visitor.visit(this);
    }

    // ---

    @Override
    public Optional<ActivationRecord> step() {
        // TODO
        return Optional.empty();
    }
    
    @Override
    public boolean isFulfilled() {
        // TODO
        return false;
    }
}
