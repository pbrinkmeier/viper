package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.model.interpreter.ComparisonActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.VariableExtractor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents an arithmetic comparison goal in an AST.
 */
public abstract class ComparisonGoal extends Goal {
    private final String symbol;
    private final String htmlSymbol;
    private final Term lhs;
    private final Term rhs;

    /**
     * Initializes a comparison goal with a left and right hand side.
     *
     * @param symbol symbol of this comparison (e.g. =:=, &gt;=)
     * @param htmlSymbol HTML version of the symbol
     * @param lhs left hand side of this comparison
     * @param rhs right hand side of this comparison
     */
    public ComparisonGoal(String symbol, String htmlSymbol, Term lhs, Term rhs) {
        super();

        this.symbol = symbol;
        this.htmlSymbol = htmlSymbol;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Getter-method for this comparisons symbol as Graphviz-compatible HTML.
     *
     * @return symbol of this comparison
     */
    public String getHtmlSymbol() {
        return this.htmlSymbol;
    }

    /**
     * Getter-method for the left hand side.
     *
     * @return left hand side
     */
    public Term getLhs() {
        return this.lhs;
    }

    /**
     * Getter-method for the right hand side.
     *
     * @return right hand side
     */
    public Term getRhs() {
        return this.rhs;
    }

    // ---

    @Override
    public ComparisonActivationRecord createActivationRecord(Interpreter interpreter,
            Optional<FunctorActivationRecord> parent) {
        return new ComparisonActivationRecord(interpreter, parent, this);
    }

    @Override
    public List<Variable> getVariables() {
        List<Variable> variables = new ArrayList<>();
        variables.addAll(this.lhs.accept(new VariableExtractor()));

        for (Variable var : this.rhs.accept(new VariableExtractor())) {
            if (!variables.contains(var)) {
                variables.add(var);
            }
        }

        return variables;
    }

    /**
     * Evaluates the comparison of two integers.
     *
     * @param lhs left hand side of the comparison
     * @param rhs right hand side of the comparison
     * @return whether the comparison is true
     */
    public abstract boolean compareNumbers(BigInteger lhs, BigInteger rhs);

    /**
     * Two comparison goals are equal if they are of the same class and their lhs
     * and rhs are equal.
     *
     * @param other object to compare to
     * @return whether this equals other
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(this.getClass())) {
            return false;
        }

        ComparisonGoal otherGoal = (ComparisonGoal) other;

        return this.lhs.equals(otherGoal.getLhs()) && this.rhs.equals(otherGoal.getRhs());
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", this.lhs, this.symbol, this.rhs);
    }
}
