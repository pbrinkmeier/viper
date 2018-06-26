package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.ast.CutGoal;

public class CutActivationRecord extends ActivationRecord {
    /**
     * @param parent 
     * @param goal
     */
    public CutActivationRecord(Optional<ActivationRecord> parent, CutGoal goal) {
    	super(null, null);
        // TODO
    }

    /**
     * @param visitor 
     * @return
     */
    public CutActivationRecord accept(CutActivationRecord visitor) {
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
	public CutActivationRecord accept(ActivationRecordVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}
}
