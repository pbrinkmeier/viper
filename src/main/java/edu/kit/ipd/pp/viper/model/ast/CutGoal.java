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
    
    /**
     * Checks whether this equals another object. Will only return true for functors
     * of the same name and parameters.
     *
     * @param other other Object
     * @return whether this is equal to object according to the rules defined above
     */
    @Override
    public boolean equals(Object other) {
        // TODO
        return false;
    }
}
