package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;

import java.util.Optional;

public abstract class Goal {
    /**
     * Creates the ActivationRecord for this goal.
     *
     * @param parent optional FunctorActivationRecord that has this as subgoal
     * @return newly created ActivationRecord
     */
    public abstract ActivationRecord createActivationRecord(Optional<FunctorActivationRecord> parent);

    /**
     * Getter-method for a string representation of this goal.
     *
     * @return string representation of this goal
     */
    @Override
    public abstract String toString();
    
    /**
     * Checks whether this equals another object. Will only return true for functors
     * of the same name and parameters.
     *
     * @param other other Object
     * @return whether this is equal to object according to the rules defined above
     */
    @Override
    public abstract boolean equals(Object other);
}
