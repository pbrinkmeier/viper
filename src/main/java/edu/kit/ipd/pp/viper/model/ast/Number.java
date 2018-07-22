package edu.kit.ipd.pp.viper.model.ast;

/**
 * Represents a number in an AST.
 */
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

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Number transform(TermTransformationVisitor visitor) {
        return visitor.visit(this);
    }

    /**
     * @return just this number
     */
    public Number evaluate() {
        return this;
    }

    /**
     * @return a string representation of this number
     */
    public String toString() {
        return String.format("%d", this.getNumber());
    }

    /**
     * @return a GraphViz-compatible HTML representation of this number
     */
    public String toHtml() {
        return this.toString();
    }

    /**
     * Checks whether this is equal to another Object. Only returns true if the
     * other Object is a Number representing the same number.
     * 
     * @param other other Object
     * @return whether this equals other according to the rules above
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(Number.class)) {
            return false;
        }

        return ((Number) other).getNumber() == this.getNumber();
    }
}
