package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Optional;

public abstract class Goal {
    /**
     * Creates the ActivationRecord for this goal.
     *
     * @param interpreter interpreter for the created ActivationRecord
     * @param parent optional FunctorActivationRecord that has this as subgoal
     * @return newly created ActivationRecord
     */
    public abstract ActivationRecord createActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent);

    /**
     * Getter-method for a string representation of this goal.
     *
     * @return string representation of this goal
     */
    @Override
    public abstract String toString();
}
