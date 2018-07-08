package edu.kit.ipd.pp.viper.model.ast;

public class NotEqualGoal extends ComparisonGoal {
    /**
     * Initializes an non-equality comparison goal with a left and right hand side.
     *
     * @param lhs left hand side of the comparison
     * @param rhs right hand side of the comparison
     */
    public NotEqualGoal(Term lhs, Term rhs) {
        super(null, null);
        // TODO
    }

    /**
     * Checks whether lhs is not equal to rhs.
     * 
     * @param lhs left hand side of the comparison
     * @param rhs right hand side of the comparison
     * @return
     */
    @Override
    public boolean compareNumbers(int lhs, int rhs) {
        // TODO
        return false;
    }

    /**
     * Getter-method for the symbol of this comparison ("=\=").
     *
     * @return a non-equals sign
     */
    @Override
    protected String getSymbol() {
        // TODO
        return null;
    }
    
    /**
     * Checks whether this equals another object. Will only return true for functors
     * of the same name and parameters.
     *
     * @param other other Object
     * @return whether this is equal to object according to the rules defined above
     */
    @Override
    public boolean equals(Object other) {
        // TODO
        return false;
    }
}
