package edu.kit.ipd.pp.viper.model.ast;

import java.util.List;

public class Functor extends Term {
    /**
     * @param name 
     * @param subTerms
     */
    public Functor(String name, List<Term> subTerms) {
        // TODO
    }

    /**
     * @return
     */
    public String getName() {
        // TODO
        return "";
    }

    /**
     * @return
     */
    public List<Term> getSubTerms() {
        // TODO
        return null;
    }

    /**
     * @param name 
     * @param subTerms 
     * @return
     */
    public Functor createNew(String name, List<Term> subTerms) {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public int getArity() {
        // TODO
        return 0;
    }

    /**
     * @param visitor 
     * @return
     */
    @Override
    public Term accept(TermVisitor<Term> visitor) {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public Number evaluate() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public String toString() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    public String toHtml() {
        // TODO
        return null;
    }
}
