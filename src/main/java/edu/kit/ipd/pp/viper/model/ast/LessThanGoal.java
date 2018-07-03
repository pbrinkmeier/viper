package edu.kit.ipd.pp.viper.model.ast;

public class LessThanGoal extends ComparisonGoal {
    /**
     * Initializes a less-than comparison goal with a left and right hand side.
     *
     * @param lhs left hand side of the comparison
     * @param rhs right hand side of the comparison
     */
    public LessThanGoal(Term lhs, Term rhs) {
        super(null, null);
        // TODO
    }

    /**
     * Checks whether lhs is less than rhs.
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
     * Getter-method for the symbol of this comparison ("&lt;").
     *
     * @return a less-than sign
     */
    @Override
    protected String getSymbol() {
        // TODO
        return null;
    }
}
