package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;

public abstract class Goal {
    /**
     * @param parent
     * @return
     */
    public abstract ActivationRecord createActivationRecord(Optional<ActivationRecord> parent);

    /**
     * @return
     */
    public abstract String toString();
}
