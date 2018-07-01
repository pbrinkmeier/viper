package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.List;
import java.util.Optional;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;

public class FunctorActivationRecord extends ActivationRecord {
    /**
     * 
     */
    private int ruleIndex;

    /**
     * @param parent
     * @param goal
     */
    public FunctorActivationRecord(Optional<ActivationRecord> parent, FunctorGoal goal) {
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
    public Functor getFunctor() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public Functor getMatchingRuleHead() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public int getRuleIndex() {
        // TODO
        return 0;
    }

    /**
     * @return
     */
    public List<ActivationRecord> getSubSteps() {
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
