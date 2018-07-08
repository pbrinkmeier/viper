package edu.kit.ipd.pp.viper.model.ast;

public class LessThanEqualGoal extends ComparisonGoal {
    /**
     * Initializes a less-than-equal comparison goal with a left and right hand side.
     *
     * @param lhs left hand side of the comparison
     * @param rhs right hand side of the comparison
     */
    public LessThanEqualGoal(Term lhs, Term rhs) {
        super(null, null);
        // TODO
    }

    /**
     * Checks whether lhs is less than or equal to rhs.
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
     * Getter-method for the symbol of this comparison ("=&lt;").
     *
     * @return a less-than/equals sign
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
