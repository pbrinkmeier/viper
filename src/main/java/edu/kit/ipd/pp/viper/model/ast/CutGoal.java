package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.interpreter.CutActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;

public class CutGoal extends Goal {
    /**
     * Creates a new cut activation record.
     *
     * @param parent optional parent ActivationRecord
     * @return new CutActivationRecord
     */
    @Override
    public CutActivationRecord createActivationRecord(Optional<FunctorActivationRecord> parent) {
        // TODO
        return null;
    }

    /**
     * Getter-method for a string representation of this cut goal.
     * Always just "!".
     *
     * @return string representation of this cut goal
     */
    @Override
    public String toString() {
        // TODO
        return null;
    }
}
