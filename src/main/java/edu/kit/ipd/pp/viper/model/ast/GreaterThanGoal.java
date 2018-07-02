package edu.kit.ipd.pp.viper.model.ast;

public class GreaterThanGoal extends ComparisonGoal {
    /**
     * Initializes a greater-than comparison goal with a left and right hand side.
     *
     * @param lhs left hand side of the comparison
     * @param rhs right hand side of the comparison
     */
    public GreaterThanGoal(Term lhs, Term rhs) {
        super(null, null);
        // TODO
    }

    /**
     * Checks whether lhs is greater than rhs.
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
     * Getter-method for the symbol of this comparison ("&gt;").
     *
     * @return a greater-than sign
     */
    @Override
    protected String getSymbol() {
        // TODO
        return null;
    }
}
