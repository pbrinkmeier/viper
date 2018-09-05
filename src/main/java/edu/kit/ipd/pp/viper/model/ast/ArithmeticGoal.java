package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.VariableExtractor;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.ArithmeticActivationRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents an arithmetic goal in an AST of the form A is B. Before unifying
 * the left and right hand sides, the right hand side is evaluated
 * arithmetically. See ArithmeticActivationRecord for more information on how
 * this kind of goal is executed.
 */
public class ArithmeticGoal extends Goal {
    private final Term lhs;
    private final Term rhs;

    /**
     * Initializes an arithmetic goal with its left and right hand side.
     *
     * @param lhs left hand side of the unification
     * @param rhs right hand side that will be evaluated before unification
     */
    public ArithmeticGoal(Term lhs, Term rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Getter-method for the left hand side
     *
     * @return left hand side
     */
    public Term getLhs() {
        return this.lhs;
    }

    /**
     * Getter-method for the right hand side
     * 
     * @return right hand side
     */
    public Term getRhs() {
        return this.rhs;
    }

    @Override
    public ArithmeticGoal transform(TermTransformationVisitor visitor) {
        return new ArithmeticGoal(this.lhs.transform(visitor), this.rhs.transform(visitor));
    }

    @Override
    public List<Variable> getVariables() {
        // TODO: this should be done using some sort of set class
        List<Variable> variables = new ArrayList<>();
        variables.addAll(this.lhs.accept(new VariableExtractor()));

        for (Variable var : this.rhs.accept(new VariableExtractor())) {
            if (!variables.contains(var)) {
                variables.add(var);
            }
        }

        return variables;
    }

    @Override
    public ArithmeticActivationRecord createActivationRecord(Interpreter interpreter,
            Optional<FunctorActivationRecord> parent) {
        return new ArithmeticActivationRecord(this, interpreter, parent);
    }

    @Override
    public String toString() {
        return String.format("%s is %s", this.lhs, this.rhs);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.lhs, this.rhs);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(ArithmeticGoal.class)) {
            return false;
        }

        ArithmeticGoal otherGoal = (ArithmeticGoal) other;

        return otherGoal.getLhs().equals(this.lhs) && otherGoal.getRhs().equals(this.rhs);
    }
}
