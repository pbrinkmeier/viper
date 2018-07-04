package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import java.util.Optional;

/**
 * Central execution unit, similar to a stack frame in procedural languages.
 *
 * The abbreviation "AR" in these files comments stands for Activation Record.
 */
public abstract class ActivationRecord {
    /**
     * General initialization for activation records.
     * Sets the interpreter and parent AR.
     *
     * @param interpreter interpreter reference
     * @param parent optional parent AR
     */
    public ActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent) {
        // TODO
    }

    /**
     * Sets the environment of this activation record.
     *
     * @param environment new environment
     */
    protected void setEnvironment(Environment environment) {
        // TODO
    }

    /**
     * Helper method for subclasses to determine the previous goal/AR.
     * If this AR has no parent, return empty.
     * If this AR has a parent and is its parents first subgoal, return the parent.
     * If this AR has a parent and a preceding subgoal, return that subgoal.
     *
     * @return previous AR for this AR, according to the rules stated above
     */
    protected Optional<ActivationRecord> getPrevious() {
        // TODO
        return null;
    }

    /**
     * Getter-method for this ARs environment.
     *
     * @return this ARs environment
     */
    protected Environment getEnvironment() {
        // TODO
        return null;
    }

    /**
     * Helper method for subclasses to determine the next goal/AR.
     * If this AR has no parent, return empty.
     * If this AR has a parent and isn't its parents last subgoal, return the next subgoal.
     * If this AR has a parent and is its parents last subgoal, return its parents getNext().
     *
     * @return next AR
     */
    protected Optional<ActivationRecord> getNext() {
        // TODO
        return null;
    }

    /**
     * Getter-method for this ARs interpreter.
     *
     * @return this ARs interpreter
     */
    protected Interpreter getInterpreter() {
        // TODO
        return null;
    }

    /**
     * Getter-method for this ARs parent.
     *
     * @return this ARs optional parent
     */
    protected Optional<FunctorActivationRecord> getParent() {
        // TODO
        return null;
    }

    /**
     * Getter-method for whether this AR has already been visited.
     *
     * @return whether this AR has already been visited
     */
    protected boolean isVisited() {
        // TODO
        return false;
    }

    /**
     * Setter-method for visited attribute.
     *
     * @param visited new visited value to set
     */
    protected void setVisited(boolean visited) {
        // TODO
    }

    /**
     * To be overwritten by subclasses (to allow them to have concrete return types).
     *
     * @return the goal that corresponds to this AR
     */
    protected abstract Goal getGoal();

    /**
     * To be overwritten by the subclasses.
     * This is actually the core of the whole execution.
     *
     * @return an optional next step to run after this one
     */
    protected abstract Optional<ActivationRecord> step();
}
