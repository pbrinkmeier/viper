package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.CutActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a cut goal in an AST.
 */
public class CutGoal extends Goal {
    @Override
    public List<Variable> getVariables() {
        return Collections.unmodifiableList(new ArrayList<>());
    }

    @Override
    public CutActivationRecord createActivationRecord(Interpreter interpreter,
            Optional<FunctorActivationRecord> parent) {
        return new CutActivationRecord(interpreter, parent, this);
    }

    /**
     * Since there are not terms in a CutGoal, it does not need to be transformed.
     */
    @Override
    public CutGoal transform(TermTransformationVisitor visitor) {
        return this;
    }

    @Override
    public String toString() {
        return "!";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash("!");
    }

    /**
     * Two CutGoals are always equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(CutGoal.class)) {
            return false;
        }

        return true;
    }
}
