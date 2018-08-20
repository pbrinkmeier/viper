package edu.kit.ipd.pp.viper.model.ast;

import java.math.BigInteger;
import java.util.List;

/**
 * Represents an arithmetic multiplication operation in an AST.
 */
public class MultiplicationOperation extends BinaryOperation {
    /**
     * Initializes a new MultiplicationOperation with a left and right hand side.
     *
     * @param lhs left hand side
     * @param rhs right hand side
     */
    public MultiplicationOperation(Term lhs, Term rhs) {
        super("*", lhs, rhs);
    }

    /**
     * Calculates the product of two integers.
     *
     * @param a left hand side
     * @param b right hand side
     * @return a times b
     */
    @Override
    protected BigInteger calculate(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    /**
     * Creates a new MultiplicationOperation object.
     *
     * @param parameters parameters of the new operation
     * @return a new MultiplicationOperation instance
     */
    @Override
    public MultiplicationOperation createNew(List<Term> parameters) {
        return new MultiplicationOperation(parameters.get(0), parameters.get(1));
    }
}
