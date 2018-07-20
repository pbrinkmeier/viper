package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.ArithmeticGoal;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.TermEvaluationException;

import java.util.Optional;

/**
 * Execution state of an arithmetic goal.
 * If an arithmetic AR has not been visited, it is not fulfilled.
 * If an arithmetic AR has been visited, but an error occurred during evaluation of the rhs, it is not fulfilled.
 * If an arithmetic AR has been visited, but unification was unsuccessful, it is not fulfilled.
 * If an unification record has been visited and unification was successful, it is fulfilled.
 */
public class ArithmeticActivationRecord extends ActivationRecord {
    private final ArithmeticGoal goal;
    private Term evaluatedRhs;
    private UnificationResult result;

    /**
     * Initializes an arithmetic activation record.
     *
     * @param goal respective arithmetic goal
     * @param interpreter interpreter this AR belongs to
     * @param parent optional parent functor AR
     */
    public ArithmeticActivationRecord(
        ArithmeticGoal goal,
        Interpreter interpreter,
        Optional<FunctorActivationRecord> parent
    ) {
        super(interpreter, parent);
        this.goal = goal;
    }

    @Override
    public ArithmeticGoal getGoal() {
        return this.goal;
    }

    /**
     * Getter-method for the left hand side of the unification with all previous substitutions applied.
     *
     * @return left hand side
     */
    public Term getLhs() {
        return this.applyPreviousSubstitutions(this.getGoal().getLhs());
    }

    /**
     * Getter-method for the right hand side before evaluation with all previous substitutions applied.
     *
     * @return right hand side
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
        // If the AR has been visited before, backtrack
        if (this.isVisited()) {
            this.setVisited(false);
            return this.getPrevious();
        }

        this.setVisited(true);

        Term lhs = this.getLhs();
        Term rhs = this.getRhs();

        try {
            this.evaluatedRhs = rhs.evaluate();
            this.result = this.evaluatedRhs.accept(lhs.accept(new UnifierCreator()));
        } catch (TermEvaluationException e) {
            this.result = UnificationResult.error(e.getMessage());
        }

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

        return this.getResult().isSuccess();
    }
}
