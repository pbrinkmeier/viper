package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.ast.ComparisonGoal;

public class ComparisonActivationRecord extends ActivationRecord {
    /**
     * @param parent 
     * @param goal
     */
    public ComparisonActivationRecord(Optional<ActivationRecord> parent, ComparisonGoal goal) {
        super(null, null);
    	// TODO
    }

    /**
     * @return
     */
    public Optional<String> getEvaluationError() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public boolean hasBeenFulfilled() {
        // TODO
        return false;
    }

    /**
     * @param visitor 
     * @return
     */
    public ComparisonActivationRecord accept(ComparisonActivationRecord visitor) {
        // TODO
        return null;
    }

    /**
     * @return
     */
    protected ActivationRecord step() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    protected ActivationRecord createCopy() {
        // TODO
        return null;
    }

	@Override
	public ActivationRecordVisitor accept(ActivationRecordVisitor visitor) {
		// TODO
		return null;
	}
}
