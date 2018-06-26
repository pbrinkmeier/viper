package edu.kit.ipd.pp.viper.model.ast;

public final class Number extends Term {
    /**
     * @param number
     */
    public Number(int number) {
        // TODO
    }

    /**
     * @return
     */
    public int getNumber() {
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
