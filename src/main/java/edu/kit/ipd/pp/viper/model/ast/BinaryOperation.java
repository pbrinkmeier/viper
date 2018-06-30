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
     * Evaluates this operation arithmetically.
     * This method simply evaluates the left and right hand side of the operation and calls calculate(a, b) on the resulting integers.
     * calculate() must be implemented in the subclasses.
     * After calling calculate, the resulting int will be wrapped in a new Number Term.
     *
     * @return new Number term with the evaluation result
     */
    @Override
    public Number evaluate() throws TermEvaluationException {
        List<Term> params = this.getParameters();

        int lhs = params.get(0).evaluate().getNumber();
        int rhs = params.get(1).evaluate().getNumber();

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
            this.getParameters().get(0),
            this.getName(),
            this.getParameters().get(1)
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
            this.getParameters().get(0).toHtml(),
            this.getName(),
            this.getParameters().get(1).toHtml()
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
