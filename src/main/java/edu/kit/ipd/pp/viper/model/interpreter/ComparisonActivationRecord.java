package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

import edu.kit.ipd.pp.viper.model.ast.ComparisonGoal;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.TermEvaluationException;

import java.util.Optional;

/**
 * Represents an arithmetic comparison goal during execution.
 * If a comparison AR has not been visited, it is not fulfilled.
 * If a comparison AR has been visited, it is fulfilled if the comparison holds.
 */
public class ComparisonActivationRecord extends ActivationRecord {
    private final ComparisonGoal goal;
    private Optional<String> errorMessage;
    private boolean fulfilled;

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
    public ComparisonGoal getGoal() {
        return this.goal;
    }

    /**
     * Getter-method for the left hand side of this comparison with all previous substitutions applied.
     *
     * @return left hand side of the comparison
     */
    public Term getLhs() {
        return this.applyPreviousSubstitutions(this.goal.getLhs());
    }

    /**
     * Getter-method for the right hand side of this comparison with all previous substitutions applied.
     *
     * @return right hand side of the comparison
     */
    public Term getRhs() {
        return this.applyPreviousSubstitutions(this.goal.getRhs());
    }

    /**
     * Getter-method for an error message describing what went wrong with the comparison.
     * WARNING: this may return if isVisited() == false.
     * If getErrorMessage().isPresent() == false, the comparison succeeded.
     *
     * @return optional error message
     */
    public Optional<String> getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public <T> T accept(ActivationRecordVisitor<T> visitor) {
        return visitor.visit(this);
    }

    // ---

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

        try {
            Number lhsNum = lhs.evaluate();
            Number rhsNum = rhs.evaluate();

            this.errorMessage
                = this.getGoal().compareNumbers(lhsNum.getNumber(), rhsNum.getNumber())
                ? Optional.empty()
                : Optional.of(LanguageManager.getInstance().getString(LanguageKey.ARITHMETIC_COMPARISON_FAILED));
        } catch (TermEvaluationException e) {
            this.errorMessage = Optional.of(e.getMessage());
        }

        if (this.errorMessage.isPresent()) {
            return Optional.of(this);
        }

        return Optional.of(this.getNext());
    }
    
    @Override
    public boolean isFulfilled() {
        if (!this.isVisited()) {
            return false;
        }

        return !this.errorMessage.isPresent();
    }
}
