package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.List;

public abstract class BinaryOperation extends Functor {
    /**
     * Initializes a binary operation with a symbol, a left and right hand side.
     *
     * @param lhs left hand side of the operation
     * @param rhs right hand side of the operation
     */
    public BinaryOperation(String symbol, Term lhs, Term rhs) {
    	super(symbol, Arrays.asList(lhs, rhs));
    }

    /**
     * Getter-method for the left hand side of the operation.
     *
     * @return left hand side of the operation
     */
    public Term getLhs() {
        return this.getParameters().get(0);
    }

    /**
     * Getter-method for the right hand side of the operation.
     *
     * @return right hand side of the operation
     */
    public Term getRhs() {
        return this.getParameters().get(1);
    }

    /**
     * Evaluates this operation arithmetically.
     * This method simply evaluates the left and right hand side of the operation and calls calculate(a, b) on the resulting integers.
     * calculate() must be implemented in the subclasses.
     * After calling calculate, the resulting int will be wrapped in a new Number Term.
     *
     * @return new Number term with the evaluation result
     */
    @Override
    public Number evaluate() throws TermEvaluationException {
        int lhs = this.getLhs().evaluate().getNumber();
        int rhs = this.getRhs().evaluate().getNumber();

        return new Number(this.calculate(lhs, rhs));
    }

    /**
     * Implements the actual operation.
     *
     * @param a left hand side of the operation
     * @param b right hand side of the operation
     * @return result of the operation
     */
    protected abstract int calculate(int a, int b);

    /**
     * Getter-method for a string representation of this operation.
     *
     * @return string representation of this operation
     */
    @Override
    public String toString() {
        return String.format("(%s %s %s)",
            this.getLhs().toString(),
            this.getName(),
            this.getRhs().toString()
        );
    }

    /**
     * Getter-method for a GraphViz-compatible HTML representation of this operation.
     *
     * @return HTML representation of this operation
     */
    @Override
    public String toHtml() {
        return String.format("(%s %s %s)",
            this.getLhs().toHtml(),
            this.getName(),
            this.getRhs().toHtml()
        );
    }

    /**
     * Creates a new operation with different parameters.
     * This must be overwritten in the operation subclasses because Term*Visitors only handle Functors, not *Operations.
     *
     * @param parameters new operation parameters
     * @return new instance of an operation
     */
    @Override
    public abstract BinaryOperation createNew(List<Term> parameters);
}
