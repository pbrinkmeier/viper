package edu.kit.ipd.pp.viper.model.ast;

public interface TermVisitor<ResultType> {
    /**
     * @param fun 
     * @return
     */
    public ResultType visit(Functor fun);

    /**
     * @param var 
     * @return
     */
    public ResultType visit(Variable var);

    /**
     * @param num 
     * @return
     */
    public ResultType visit(Number num);
}
