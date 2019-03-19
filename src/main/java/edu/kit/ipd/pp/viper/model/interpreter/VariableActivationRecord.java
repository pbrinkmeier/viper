package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.VariableGoal;
import java.util.Optional;

public class VariableActivationRecord extends ActivationRecord {
    private final VariableGoal goal;
    private Optional<ActivationRecord> ar;

    public VariableActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent, VariableGoal goal) {
        super(interpreter, parent);
        this.goal = goal;
        this.ar = Optional.empty();
    }

    public Optional<ActivationRecord> getAr() {
        return this.ar;
    }

    @Override
    public VariableGoal getGoal() {
        return this.goal;
    }

    @Override
    protected ActivationRecord getRightmost() {
        return this.getAr().isPresent() ? this.getAr().get().getRightmost() : this;
    }

    @Override
    protected boolean represents(ActivationRecord otherAr) {
        return this == otherAr || this.getAr().isPresent() && this.getAr().get() == otherAr;
    }

    @Override
    public <T> T accept(ActivationRecordVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<ActivationRecord> step() {
        if (!this.isVisited()) {
            this.ar = this.createAr(this.applyPreviousSubstitutions(this.goal.getVariable()));
        }

        this.setVisited(true);

        // variable couldn't be converted to AR.
        if (!this.ar.isPresent()) {
            this.setVisited(false);
            return this.getPrevious();
        }

        return this.ar.get().step();
    }

    @Override
    public boolean isFulfilled() {
        return this.ar.isPresent() && this.ar.get().isFulfilled();
    }

    public Optional<ActivationRecord> createAr(Term t) {
        return t.accept(new TermToArConverter(this.getInterpreter(), this.getParent()));
    }
}
