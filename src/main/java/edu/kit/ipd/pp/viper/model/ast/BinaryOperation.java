package edu.kit.ipd.pp.viper.model.ast;

import java.util.List;

public abstract class BinaryOperation extends Functor {
    /**
     * @param lhs 
     * @param rhs
     */
    public BinaryOperation(Term lhs, Term rhs) {
    	super(null, null);
        // TODO
    }

    /**
     * @param lhs 
     * @param rhs 
     * @return
     */
    public Number evaluate(Term lhs, Term rhs) {
        // TODO
        return null;
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
     * @return
     */
    public String toString() {
        // TODO
        return "";
    }

    /**
     * @return
     */
    public String toHtml() {
        // TODO
        return "";
    }

    /**
     * @param head 
     * @param subTerms 
     * @return
     */
    public abstract BinaryOperation createNew(String head, List<Term> subTerms);
}
