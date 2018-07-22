package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.CutGoal;

import java.util.Optional;

public class CutActivationRecord extends ActivationRecord {
    private CutGoal goal;

    /**
     * Initializes a cut activation record with an interpreter, an optional parent
     * and its corresponding cut goal.
     *
     * @param interpreter interpreter this AR belongs to
     * @param parent optional parent FAR
     * @param goal corresponding cut goal from the AST
     */
    public CutActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent, CutGoal goal) {
        super(interpreter, parent);
        this.goal = goal;
    }

    @Override
    public CutGoal getGoal() {
        return this.goal;
    }

    @Override
    public <T> T accept(ActivationRecordVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isFulfilled() {
        return this.isVisited();
    }

    @Override
    public Optional<ActivationRecord> step() {
        // if a Cut has already been visited, it will backtrack to the subgoal
        // previous to its parent
        if (this.isVisited()) {
            // if this has no parent, end execution
            // (this happens if and only if the user made a query that was a single cut)
            if (!this.getParent().isPresent()) {
                this.setVisited(false);
                return Optional.empty();
            }

            // if this has a parent but nothing before that, end execution
            if (!this.getParent().get().getPrevious().isPresent()) {
                for (ActivationRecord child : this.getParent().get().getChildren()) {
                    child.setVisited(false);
                }
                this.getParent().get().setVisited(false);

                return Optional.empty();
            }

            // if a previous subgoal exists, mark all subgoals until that one as not visited
            // and continue exection there
            ActivationRecord toUnvisit = this;
            while (toUnvisit != this.getParent().get().getPrevious().get()) {
                toUnvisit.setVisited(false);
                toUnvisit = toUnvisit.getPrevious().get();
            }

            return this.getParent().get().getPrevious();
        }

        // if it hasn't, mark it as visited and return the next subgoal
        this.setVisited(true);
        return Optional.of(this.getNext());
    }
}
