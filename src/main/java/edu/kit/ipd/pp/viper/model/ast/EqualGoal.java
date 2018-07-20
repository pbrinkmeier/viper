package edu.kit.ipd.pp.viper.model.ast;

/**
 * Represents an equality goal (A =:= B) in an AST.
 */
public class EqualGoal extends ComparisonGoal {
    /**
     * Initializes an equality goal with a left and right hand side.
     *
     * @param lhs left hand side of this comparison
     * @param rhs right hand side of this comparison
     */
    public EqualGoal(Term lhs, Term rhs) {
        super("=:=", lhs, rhs);
    }

    @Override
    public EqualGoal transform(TermTransformationVisitor transformer) {
        return new EqualGoal(this.getLhs().transform(transformer), this.getRhs().transform(transformer));
    }

    @Override
    public boolean compareNumbers(int lhs, int rhs) {
        return lhs == rhs;
    }
}
