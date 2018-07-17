package edu.kit.ipd.pp.viper.model.interpreter;

public interface ActivationRecordVisitor<T> {
    /**
     * How to handle a visit of a functor activation record.
     *
     * @param far functor activation record to visit
     * @return result of the visit
     */
    T visit(FunctorActivationRecord far);

    /**
     * How to handle a visit of a functor activation record.
     *
     * @param uar unification activation record to visit
     * @return result of this visit
     */
    T visit(UnificationActivationRecord uar);
}
