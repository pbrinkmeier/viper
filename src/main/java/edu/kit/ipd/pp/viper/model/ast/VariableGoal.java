package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class VariableGoal extends Goal {
    private final Variable variable;

    /**
     * Initializes a variable goal with it's variable.
     *
     * @param variable the variable to initialize to goal with.
     */
    public VariableGoal(Variable variable) {
        this.variable = variable;
    }

    /**
     * Getter-method for the single variable representing this goal.
     * @return the single variable representing this goal
     */
    public Variable getVariable() {
        return this.variable;
    }

    @Override
    public VariableGoal transform(TermTransformationVisitor visitor) {
        return new VariableGoal(this.variable.transform(visitor));
    }

    @Override
    public Set<Variable> getVariables() {
        Set<Variable> variables = new HashSet<>();
        variables.add(this.variable);

        return Collections.unmodifiableSet(variables);
    }

    @Override
    public ActivationRecord createActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent) {
        return null;
    }

    @Override
    public String toString() {
        return this.variable.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.variable);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(VariableGoal.class)) {
            return false;
        }

        VariableGoal otherVarGoal = (VariableGoal) other;

        return otherVarGoal.variable.equals(this.variable);
    }
}
