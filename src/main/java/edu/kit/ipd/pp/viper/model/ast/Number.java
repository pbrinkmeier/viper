package edu.kit.ipd.pp.viper.model.ast;

public final class Number extends Term {
    private final int number;

    /**
     * Creates a new (immutable) number term.
     *
     * @param number Number this term represents
     */
    public Number(int number) {
        this.number = number;
    }

    /**
     * Getter-method for the number this term represents.
     *
     * @return number this term represents
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * @param visitor 
     * @return
     */
    @Override
    public <ResultType> ResultType accept(TermVisitor<ResultType> visitor) {
        return visitor.visit(this);
    }

    /**
     * @return just this number
     */
    public Number evaluate() throws TermEvaluationException {
        return this;
    }

    /**
     * @return
     */
    public String toString() {
        return String.format("%d", this.getNumber());
    }

    /**
     * @return
     */
    public String toHtml() {
        return this.toString();
    }
}
