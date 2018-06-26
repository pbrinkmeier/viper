package edu.kit.ipd.pp.viper.model.ast;

public interface TermTransformationVisitor {
    /**
     * @param functor 
     * @return
     */
    public Functor visit(Functor functor);

    /**
     * @param var 
     * @return
     */
    public Variable visit(Variable var);

    /**
     * @param num 
     * @return
     */
    public Number visit(Number num);
}
