package edu.kit.ipd.pp.viper.model.ast;

public interface TermVisitor<T> {
    /**
     * Describes the visitors behavior when it visits a functor.
     *
     * @param functor functor term to visit
     * @return result of the visit
     */
    T visit(Functor functor);

    /**
     * Describes the visitors behavior when it visits a variable.
     *
     * @param variable variable term to visit
     * @return result of the visit
     */
    T visit(Variable variable);

    /**
     * Describes the visitors behavior when it visits a number.
     *
     * @param number number term to visit
     * @return result of the visit
     */
    T visit(Number number);
}
