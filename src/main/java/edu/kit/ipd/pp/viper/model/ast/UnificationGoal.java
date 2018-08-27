package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.VariableExtractor;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.UnificationActivationRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An unification goal. This represent a Prolog goal of the form A = B.
 */
public class UnificationGoal extends Goal {
    private final Term lhs;
    private final Term rhs;

    /**
     * Initializes an unification goal with its left and right hand side.
     *
     * @param lhs left hand side of the unification
     * @param rhs right hand side of the unification
     */
    public UnificationGoal(Term lhs, Term rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Getter-method for the left hand side of the unification.
     *
     * @return left hand side of the unification
     */
    public Term getLhs() {
        return this.lhs;
    }

    /**
     * Getter-method for the right hand side of the unification.
     * 
     * @return right hand side of the unification
     */
    public Term getRhs() {
        return this.rhs;
    }

    @Override
    public UnificationGoal transform(TermTransformationVisitor visitor) {
        return new UnificationGoal(this.lhs.transform(visitor), this.rhs.transform(visitor));
    }

    @Override
    public List<Variable> getVariables() {
        // TODO: this should be done using some sort of set class
        List<Variable> variables = new ArrayList<Variable>();

        for (Variable var : this.lhs.accept(new VariableExtractor())) {
            // We don't need to check for contains here, since VariableExtractor returns unique lists
            variables.add(var);
        }

        for (Variable var : this.rhs.accept(new VariableExtractor())) {
            if (!variables.contains(var)) {
                variables.add(var);
            }
        }

        return variables;
    }

    @Override
    public UnificationActivationRecord createActivationRecord(Interpreter interpreter,
            Optional<FunctorActivationRecord> parent) {
        return new UnificationActivationRecord(this, interpreter, parent);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", this.lhs, this.rhs);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(UnificationGoal.class)) {
            return false;
        }

        UnificationGoal otherGoal = (UnificationGoal) other;

        return otherGoal.getLhs().equals(this.lhs) && otherGoal.getRhs().equals(this.rhs);
    }
}
