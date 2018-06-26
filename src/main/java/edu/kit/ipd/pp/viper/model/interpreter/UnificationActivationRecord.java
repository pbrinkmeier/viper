package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.UnificationGoal;

public class UnificationActivationRecord extends ActivationRecord {
    /**
     * @param parent 
     * @param goal
     */
    public UnificationActivationRecord(Optional<ActivationRecord> parent, UnificationGoal goal) {
        super(null, null);
    	// TODO
    }

    /**
     * @return
     */
    public Optional<UnificationResult> getUnificationResult() {
        // TODO
        return null;
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
     * @param visitor 
     * @return
     */
    public FunctorActivationRecord accept(ActivationRecordVisitor visitor) {
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
}
