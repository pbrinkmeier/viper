package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;

import java.util.Optional;

public class FunctorGoal extends Goal {
    private final Functor functor;

    /**
     * Initializes a functor goal with its functor.
     *
     * @param functor functor to fulfill against a knowledgebase
     */
    public FunctorGoal(Functor functor) {
        this.functor = functor;
    }

    /**
     * Getter-method for this goals functor
     *
     * @return functor this goal tries to fulfill against a knowledgebase
     */
    public Functor getFunctor() {
        return this.functor;
    }

    /**
     * Creates a new FunctorActivationRecord for this goal.
     *
     * @param parent optional parent ActivationRecord
     * @return new FunctorActivationRecord
     */
    @Override
    public FunctorActivationRecord createActivationRecord(Optional<FunctorActivationRecord> parent) {
        return new FunctorActivationRecord(parent, this);
    }

    /**
     * Getter-method for a string representation of this goal.
     * This equals the string representation of this goals functor.
     *
     * @return string representation of this goal.
     */
    @Override
    public String toString() {
        return this.getFunctor().toString();
    }
}
