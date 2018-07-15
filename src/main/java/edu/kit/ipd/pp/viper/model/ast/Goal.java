package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.List;
import java.util.Optional;

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
     * Returns an immutable list of variables that appear in this goal. This list
     * may be used by the interpreter manager to find out which variables a certain
     * interpreter is trying to solve for.
     *
     * @return list of variables that appear in this goal
     */
    public abstract List<Variable> getVariables();

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
