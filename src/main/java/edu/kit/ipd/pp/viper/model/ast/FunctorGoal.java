package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;

import java.util.Optional;

public class FunctorGoal extends Goal {
    /**
     * Initializes a functor goal with its functor.
     *
     * @param goal functor to fulfill against a knowledgebase
     */
    public FunctorGoal(Functor goal) {
        // TODO
    }

    /**
     * Getter-method for this goals functor
     *
     * @return functor this goal tries to fulfill against a knowledgebase
     */
    public Functor getFunctor() {
        // TODO
        return null;
    }

    /**
     * Creates a new FunctorActivationRecord for this goal.
     *
     * @param parent optional parent ActivationRecord
     * @return new FunctorActivationRecord
     */
    @Override
    public FunctorActivationRecord createActivationRecord(Optional<FunctorActivationRecord> parent) {
        // TODO
        return null;
    }

    /**
     * Getter-method for a string representation of this goal.
     * This equals the string representation of this goals functor.
     *
     * @return string representation of this goal.
     */
    @Override
    public String toString() {
        // TODO
        return null;
    }
}
