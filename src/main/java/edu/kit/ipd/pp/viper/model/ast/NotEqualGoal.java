package edu.kit.ipd.pp.viper.model.ast;

/**
 * Represents an inequality goal (A =\= B) in an AST.
 */
public class NotEqualGoal extends ComparisonGoal {
    /**
     * Initializes an inequality goal with a left and right hand side.
     *
     * @param lhs left hand side of this comparison
     * @param rhs right hand side of this comparison
     */
    public NotEqualGoal(Term lhs, Term rhs) {
        super("=\\=", lhs, rhs);
    }

    @Override
    public NotEqualGoal transform(TermTransformationVisitor transformer) {
        return new NotEqualGoal(this.getLhs().transform(transformer), this.getRhs().transform(transformer));
    }

    @Override
    public boolean compareNumbers(int lhs, int rhs) {
        return lhs != rhs;
    }
}
