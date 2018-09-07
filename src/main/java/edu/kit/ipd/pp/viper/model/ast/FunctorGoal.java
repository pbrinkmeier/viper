package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.VariableExtractor;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a functor goal in an AST. A functor goal is a single functor that
 * the interpreter tries to resolve in its knowledge base. See
 * {@link edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord} for
 * more information on how this kind of goal is treated during execution.
 */
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

    @Override
    public FunctorGoal transform(TermTransformationVisitor visitor) {
        return new FunctorGoal(this.getFunctor().transform(visitor));
    }

    /**
     * Creates a new FunctorActivationRecord for this goal. The interpreter
     * reference is passed in order for the activation record to be able to look up
     * things in the interpreters knowledge base.
     *
     * @param interpreter interpreter for the created activation record
     * @param parent optional parent ActivationRecord
     * @return new FunctorActivationRecord
     */
    @Override
    public FunctorActivationRecord createActivationRecord(Interpreter interpreter,
            Optional<FunctorActivationRecord> parent) {
        return new FunctorActivationRecord(interpreter, parent, this);
    }

    @Override
    public Set<Variable> getVariables() {
        return this.functor.accept(new VariableExtractor());
    }

    /**
     * Getter-method for a string representation of this goal. This equals the
     * string representation of this goals functor.
     *
     * @return string representation of this goal.
     */
    @Override
    public String toString() {
        return this.getFunctor().toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.functor);
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
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(FunctorGoal.class)) {
            return false;
        }

        FunctorGoal otherGoal = (FunctorGoal) other;

        return this.functor.equals(otherGoal.getFunctor());
    }
}
