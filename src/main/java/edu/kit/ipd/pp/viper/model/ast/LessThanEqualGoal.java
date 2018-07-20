package edu.kit.ipd.pp.viper.model.ast;

/**
 * Represents a less-than-equal goal (A =&lt; B) in an AST.
 */
public class LessThanEqualGoal extends ComparisonGoal {
    /**
     * Initializes a less-than-equal goal with a left and right hand side.
     *
     * @param lhs left hand side of this comparison
     * @param rhs right hand side of this comparison
     */
    public LessThanEqualGoal(Term lhs, Term rhs) {
        super("=<", lhs, rhs);
    }

    @Override
    public LessThanEqualGoal transform(TermTransformationVisitor transformer) {
        return new LessThanEqualGoal(this.getLhs().transform(transformer), this.getRhs().transform(transformer));
    }

    @Override
    public boolean compareNumbers(int lhs, int rhs) {
        return lhs <= rhs;
    }
}
