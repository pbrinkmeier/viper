package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.ast.ArithmeticGoal;
import edu.kit.ipd.pp.viper.model.ast.Term;

public class ArithmeticActivationRecord extends ActivationRecord {
    /**
     * @param parent
     * @param goal
     */
    public ArithmeticActivationRecord(Optional<ActivationRecord> parent, ArithmeticGoal goal) {
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
     * @return
     */
    public Optional<String> getEvaluationError() {
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
