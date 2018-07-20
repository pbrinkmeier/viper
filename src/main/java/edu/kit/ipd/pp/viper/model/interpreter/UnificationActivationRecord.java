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
    private final UnificationGoal goal;
    private UnificationResult result;

    /**
     * Initializes an unification activation record.
     *
     * @param goal respective unification goal
     * @param interpreter interpreter this AR belongs to
     * @param parent optional parent functor AR
     */
    public UnificationActivationRecord(
        UnificationGoal goal,
        Interpreter interpreter,
        Optional<FunctorActivationRecord> parent
    ) {
        super(interpreter, parent);
        this.goal = goal;
    }

    @Override
    public UnificationGoal getGoal() {
        return this.goal;
    }

    /**
     * Getter-method for the left hand side of this unification with all previous substitutions applied.
     *
     * @return left hand side of this unification
     */
    public Term getLhs() {
        return this.applyPreviousSubstitutions(this.getGoal().getLhs());
    }

    /**
     * Getter-method for the right hand side of this unification with all previous substitutions applied.
     *
     * @return right hand side of this unification
     */
    public Term getRhs() {
        return this.applyPreviousSubstitutions(this.getGoal().getRhs());
    }

    @Override
    public <T> T accept(ActivationRecordVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<ActivationRecord> step() {
        // if this AR has been visited before, backtrack
        if (this.isVisited()) {
            this.setVisited(false);
            return this.getPrevious();
        }

        this.setVisited(true);

        Term lhs = this.getLhs();
        Term rhs = this.getRhs();

        this.result = rhs.accept(lhs.accept(new UnifierCreator()));

        // Re-visit this AR and fail
        if (!this.result.isSuccess()) {
            return Optional.of(this);
        }

        this.setEnvironment(new Environment(this, this.result.getSubstitutions()));

        return Optional.of(this.getNext());
    }

    /**
     * Getter-method for the result of the unification.
     * This may return null if isVisited() == false.
     *
     * @return result of the unification
     */
    public UnificationResult getResult() {
        return this.result;
    }

    @Override
    public boolean isFulfilled() {
        if (!this.isVisited()) {
            return false;
        }

        return this.result.isSuccess();
    }
}
