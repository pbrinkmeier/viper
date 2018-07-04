package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;

import java.util.Optional;

public class FunctorActivationRecord extends ActivationRecord {
    /**
     * Initializes a functor activation record with an interpreter, a parent and a functor goal.
     * Internally, this already fetches all matching rules from the knowledgebase.
     *
     * @param interpreter interpreter reference (for access to the knowledgebase)
     * @param parent optional parent AR
     * @param goal goal that corresponds to this AR
     */
    public FunctorActivationRecord(
        Interpreter interpreter,
        Optional<FunctorActivationRecord> parent,
        FunctorGoal goal
    ) {
        super(null, null);
        // TODO
    }

    /**
     * Getter-method for the functor goal that corresponds to this functor AR.
     */
    @Override
    public FunctorGoal getGoal() {
        // TODO
        return null;
    }

    /**
     * Executes a single step of a functor AR.
     * If this AR has not been visited, set the index of the internal matching rule to 0.
     * If the index of the internal matching rule does not point into the list of matching rules,
     * set visited to false and return getPrevious().
     * If there is a matching rule at the current index, try to unify it with the functor of the functor goal.
     * Increase the index.
     * If unification failed, return this AR, thus trying it again.
     * If unification was successful, create a new environment for this AR with the resulting substitutions.
     * Get the subgoals and sets their corresponding ARs as this ARs children.
     * If there are any subgoals, return the first subgoal; else, return this.
     *
     * @return an optional next step (may be empty if the absolute end has been reached)
     */
    @Override
    public Optional<ActivationRecord> step() {
        // TODO
        return null;
    }
}
