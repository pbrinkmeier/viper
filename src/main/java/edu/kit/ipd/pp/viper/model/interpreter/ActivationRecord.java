package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.Term;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Central execution unit, similar to a stack frame in procedural languages.
 *
 * The abbreviation "AR" in these files comments stands for Activation Record.
 */
public abstract class ActivationRecord {
    private final Interpreter interpreter;
    private final Optional<FunctorActivationRecord> parent;
    private Environment environment;
    private boolean visited;

    /**
     * General initialization for activation records. Sets the interpreter and
     * parent AR.
     *
     * @param interpreter interpreter reference
     * @param parent optional parent AR
     */
    public ActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent) {
        this.interpreter = interpreter;
        this.parent = parent;
        this.setEnvironment(new Environment(this, Arrays.asList()));
    }

    /**
     * Sets the environment of this activation record.
     *
     * @param environment new environment
     */
    protected void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Helper method for subclasses to determine the previous goal/AR. If this AR
     * has no parent, return empty. If this AR has a parent and is its parents first
     * subgoal, return the parent. If this AR has a parent and a preceding subgoal,
     * return that subgoals rightmost subgoal.
     *
     * @return previous AR for this AR, according to the rules stated above
     */
    protected Optional<ActivationRecord> getPrevious() {
        // no parent -> no previous goal
        if (!this.getParent().isPresent()) {
            return Optional.empty();
        }

        List<ActivationRecord> children = this.getParent().get().getChildren();

        // first child -> backtrack to parent
        if (children.get(0) == this) {
            return Optional.of(this.getParent().get());
        }

        // not first child -> backtrack to previous child
        // find "this" in child list and return the previous childs rightmost subgoal
        for (int index = 1; index < children.size(); index++) {
            if (children.get(index) == this) {
                return Optional.of(children.get(index - 1).getRightmost());
            }
        }

        // This can never be reached. Java compiler complains when it's missing tho.
        return null;
    }

    /**
     * Applies all previous substitutions in a term. This method comes in handy for
     * any AR that needs to apply previous substitutions, which is basically all of
     * them.
     *
     * @param t term to apply substitutions in
     * @return t but with all previous substitutions applied
     */
    protected Term applyPreviousSubstitutions(Term t) {
        if (this.getPrevious().isPresent()) {
            return this.getPrevious().get().getEnvironment().applyAllSubstitutions(t);
        }

        return t;
    }

    /**
     * Returns just this AR, since ARs usually do not have children. This is used
     * only in {@link #getPrevious()}. It is meant to be overwritten by
     * {@link FunctorActivationRecord#getRightmost()} to find the visually rightmost
     * ActivationRecord in a tree.
     *
     * @return rightmost AR in the tree spanned by this AR
     */
    protected ActivationRecord getRightmost() {
        return this;
    }

    /**
     * Getter-method for this ARs environment.
     *
     * @return this ARs environment
     */
    public Environment getEnvironment() {
        return this.environment;
    }

    /**
     * Helper method for subclasses to determine the next goal/AR. If this AR has no
     * parent, return the rightmost member of its subtree. If this AR has a parent
     * and isn't its parents last subgoal, return the next subgoal. If this AR has a
     * parent and is its parents last subgoal, return its parents getNext().
     *
     * @return next AR
     */
    protected ActivationRecord getNext() {
        if (!this.getParent().isPresent()) {
            return this.getRightmost();
        }

        List<ActivationRecord> children = this.getParent().get().getChildren();
        for (int index = 0; index < children.size() - 1; index++) {
            if (children.get(index) == this) {
                return children.get(index + 1);
            }
        }

        return this.getParent().get().getNext();
    }

    /**
     * Getter-method for this ARs interpreter.
     *
     * @return this ARs interpreter
     */
    protected Interpreter getInterpreter() {
        return this.interpreter;
    }

    /**
     * Getter-method for this ARs parent.
     *
     * @return this ARs optional parent
     */
    public Optional<FunctorActivationRecord> getParent() {
        return this.parent;
    }

    /**
     * Getter-method for whether this AR has already been visited.
     *
     * @return whether this AR has already been visited
     */
    public boolean isVisited() {
        return this.visited;
    }

    /**
     * Setter-method for visited attribute. If visited will be set to false, the
     * environment of this AR will be reset too.
     *
     * @param visited new visited value to set
     */
    protected void setVisited(boolean visited) {
        this.environment = new Environment(this, Arrays.asList());

        this.visited = visited;
    }

    /**
     * Interface for ActivationRecordVisitors.
     *
     * @param <T> visit result type
     * @param visitor visitor to visit this activation record
     * @return result of the visit
     */
    public abstract <T> T accept(ActivationRecordVisitor<T> visitor);

    /**
     * Checks whether this AR is fulfilled. "Fulfilled" means that itself was a
     * success and all its subgoals too. Take a look at the comments in the
     * implementing subclasses for more information.
     *
     * @return whether this AR is fulfilled
     */
    protected abstract boolean isFulfilled();

    /**
     * To be overwritten by subclasses (to allow them to have concrete return
     * types).
     *
     * @return the goal that corresponds to this AR
     */
    protected abstract Goal getGoal();

    /**
     * To be overwritten by the subclasses. This is actually the core of the whole
     * execution.
     *
     * @return an optional next step to run after this one
     */
    protected abstract Optional<ActivationRecord> step();
}
