package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.List;

public class AdditionOperation extends BinaryOperation {
    /**
     * Initializes a new AdditionOperation with a left and right hand side.
     *
     * @param lhs left hand side
     * @param rhs right hand side
     */
    public AdditionOperation(Term lhs, Term rhs) {
    	super("+", lhs, rhs);
    }

    /**
     * Calculates the sum of two integers and return the result.
     *
     * @param a left summand
     * @param b right summand
     * @return sum of a and b
     */
    protected int calculate(int a, int b) {
        return a + b;
    }

    /**
     * Creates a new AdditionOperation object
     * @param parameters parameters of the new AdditionOperation
     * @return
     */
    public AdditionOperation createNew(List<Term> parameters) {
        return new AdditionOperation(parameters.get(0), parameters.get(1));
    }
}
