package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

public final class Variable extends Term {
    /**
     * @param name 
     * @param index
     */
    public Variable(String name, Integer index) {
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
    public Optional<Integer> getIndex() {
        // TODO
        return null;
    }

    /**
     * @param visitor 
     * @return
     */
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
