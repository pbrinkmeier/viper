package edu.kit.ipd.pp.viper.model.ast;

import java.math.BigInteger;

/**
 * Represents a greater-than goal (A &gt; B) in an AST.
 */
public class GreaterThanGoal extends ComparisonGoal {
    /**
     * Initializes a greater-than goal with a left and right hand side.
     *
     * @param lhs left hand side of this comparison
     * @param rhs right hand side of this comparison
     */
    public GreaterThanGoal(Term lhs, Term rhs) {
        super(">", "&gt;", lhs, rhs);
    }

    @Override
    public GreaterThanGoal transform(TermTransformationVisitor transformer) {
        return new GreaterThanGoal(this.getLhs().transform(transformer), this.getRhs().transform(transformer));
    }

    @Override
    public boolean compareNumbers(BigInteger lhs, BigInteger rhs) {
        return lhs.compareTo(rhs) > 0;
    }
}
