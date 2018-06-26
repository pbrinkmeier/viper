package edu.kit.ipd.pp.viper.model.interpreter;

public interface ActivationRecordVisitor<ResultType> {
    /**
     * @param functorAr 
     * @return
     */
    ResultType visit(FunctorActivationRecord functorAr);

    /**
     * @param unificationAr 
     * @return
     */
    ResultType visit(UnificationActivationRecord unificationAr);

    /**
     * @param cutAr 
     * @return
     */
    ResultType visit(CutActivationRecord cutAr);

    /**
     * @param arithAr 
     * @return
     */
    ResultType visit(ArithmeticActivationRecord arithAr);

    /**
     * @param compAr 
     * @return
     */
    ResultType visit(ComparisonActivationRecord compAr);
}
