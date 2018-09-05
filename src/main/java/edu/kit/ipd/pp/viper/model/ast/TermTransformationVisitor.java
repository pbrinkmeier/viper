package edu.kit.ipd.pp.viper.model.ast;

/**
 * Visitor to transform terms
 */
public interface TermTransformationVisitor {
    /**
     * How to transform a functor.
     *
     * @param functor functor to transform
     * @return transformed functor
     */
    Functor visit(Functor functor);

    /**
     * How to transform a variable.
     *
     * @param variable variable to transform
     * @return transformed variable
     */
    Variable visit(Variable variable);

    /**
     * @param number number to transform
     * @return transformed number
     */
    Number visit(Number number);
}
