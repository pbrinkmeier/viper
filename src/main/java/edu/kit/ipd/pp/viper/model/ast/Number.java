package edu.kit.ipd.pp.viper.model.ast;

import java.math.BigInteger;

/**
 * Represents a number in an AST.
 */
public final class Number extends Term {
    private final BigInteger number;

    /**
     * Creates a new immutable number term from an integer.
     * This constructor is kept for convenience while testing only.
     *
     * @param number number this term represents (as an integer)
     */
    public Number(int number) {
        this(BigInteger.valueOf(number));
    }

    /**
     * Creates a new immutable number term from a BigInteger.
     *
     * @param number number this term represents (as an instance of {@link java.math.BigInteger})
     */
    public Number(BigInteger number) {
        this.number = number;
    }

    /**
     * Getter-method for the number this term represents.
     *
     * @return number this term represents
     */
    public BigInteger getNumber() {
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
    @Override
    public Number evaluate() {
        return this;
    }

    /**
     * @return a string representation of this number
     */
    @Override
    public String toString() {
        return this.number.toString();
    }

    /**
     * @return a GraphViz-compatible HTML representation of this number
     */
    @Override
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

        return ((Number) other).getNumber().equals(this.getNumber());
    }
}
