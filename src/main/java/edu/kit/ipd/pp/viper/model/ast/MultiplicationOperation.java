package edu.kit.ipd.pp.viper.model.ast;

import java.util.List;

public class MultiplicationOperation extends BinaryOperation {
    /**
     * @param lhs 
     * @param rhs
     */
    public MultiplicationOperation(Term lhs, Term rhs) {
    	super(null, null);
        // TODO
    }

    /**
     * @param a 
     * @param b 
     * @return
     */
    protected int calculate(int a, int b) {
        // TODO
        return 0;
    }

    /**
     * @param head 
     * @param subTerms 
     * @return
     */
    public BinaryOperation createNew(String head, List<Term> subTerms) {
        // TODO
        return null;
    }
}
