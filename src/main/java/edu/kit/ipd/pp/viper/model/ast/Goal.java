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
    public abstract ActivationRecord createActivationRecord(
        Interpreter interpreter,
        Optional<FunctorActivationRecord> parent
    );

    /**
     * Applies a TermTransformationVisitor to all terms in this goal.
     * This allows {@link edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord#step()} to indexify
     * the subgoals of a matching rule.
     *
     * @param visitor {@link TermTransformationVisitor} to visit this goal
     * @return new version of the goal where the visitor has been applied to all terms
     */
    public abstract Goal transform(TermTransformationVisitor visitor);

    /**
     * Getter-method for a string representation of this goal.
     *
     * @return string representation of this goal
     */
    @Override
    public abstract String toString();
}
