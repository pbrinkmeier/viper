package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.Set;
import java.util.Optional;

/**
 * Represents a goal in an AST. Subclasses must provide a means to create a
 * respective activation record for execution via createActivationRecord.
 */
public abstract class Goal {
    /**
     * Creates the ActivationRecord for this goal.
     *
     * @param interpreter interpreter for the created ActivationRecord
     * @param parent optional FunctorActivationRecord that has this as subgoal
     * @return newly created ActivationRecord
     */
    public abstract ActivationRecord createActivationRecord(Interpreter interpreter,
            Optional<FunctorActivationRecord> parent);

    /**
     * Applies a TermTransformationVisitor to all terms in this goal. This allows
     * {@link edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord#step()}
     * to indexify the subgoals of a matching rule.
     *
     * @param visitor {@link TermTransformationVisitor} to visit this goal
     * @return new version of the goal where the visitor has been applied to all
     *         terms
     */
    public abstract Goal transform(TermTransformationVisitor visitor);

    /**
     * Returns an immutable set of variables that appear in this goal. This set
     * may be used by the interpreter manager to find out which variables a certain
     * interpreter is trying to solve for.
     *
     * @return set of variables that appear in this goal
     */
    public abstract Set<Variable> getVariables();

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
