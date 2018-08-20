package edu.kit.ipd.pp.viper.model.ast;

import java.math.BigInteger;

/**
 * Represents a less-than goal (A &lt; B) in an AST.
 */
public class LessThanGoal extends ComparisonGoal {
    /**
     * Initializes a less-than goal with a left and right hand side.
     *
     * @param lhs left hand side of this comparison
     * @param rhs right hand side of this comparison
     */
    public LessThanGoal(Term lhs, Term rhs) {
        super("<", "&lt;", lhs, rhs);
    }

    @Override
    public LessThanGoal transform(TermTransformationVisitor transformer) {
        return new LessThanGoal(this.getLhs().transform(transformer), this.getRhs().transform(transformer));
    }

    @Override
    public boolean compareNumbers(BigInteger lhs, BigInteger rhs) {
        return lhs.compareTo(rhs) < 0;
    }
}
