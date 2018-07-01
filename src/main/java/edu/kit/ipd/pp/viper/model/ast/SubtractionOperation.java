package edu.kit.ipd.pp.viper.model.ast;

import java.util.List;

public class SubtractionOperation extends BinaryOperation {
    /**
     * Initializes a SubtractionOperation with a left and right hand side.
     *
     * @param lhs left hand side
     * @param rhs right hand side
     */
    public SubtractionOperation(Term lhs, Term rhs) {
    	super("-", lhs, rhs);
    }

    /**
     * Implements subtraction.
     *
     * @param a left hand side
     * @param b right hand side
     * @return the result of the expression a - b
     */
    protected int calculate(int a, int b) {
        return a - b;
    }

    /**
     * Creates a new SubtractionOperation.
     * Meant to be used in TermVisitors, which do not explcitly visit Operations.
     *
     * @param parameters new parameters for the new SubstractionOperation (expected to have exactly two elements)
     * @return new SubtractionOperation object
     */
    public BinaryOperation createNew(List<Term> parameters) {
        return new SubtractionOperation(parameters.get(0), parameters.get(1));
    }
}
