package edu.kit.ipd.pp.viper.model.ast;

public abstract class Term {
    /**
     * @param visitor 
     * @return
     */
    public abstract Term accept(TermVisitor<Term> visitor);

    /**
     * @return
     */
    public abstract Number evaluate();

    /**
     * @return
     */
    public abstract String toString();

    /**
     * @return
     */
    public abstract String toHtml();
}
