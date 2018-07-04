package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

public final class Variable extends Term {
    private final String name;
    private final Optional<Integer> index;

    /**
     * Initializes a new variable with just a name, no index.
     *
     * @param name the variables name
     */
    public Variable(String name) {
        this.name = name;
        this.index = Optional.empty();
    }

    /**
     * Initializes a new variable with a name and an index.
     *
     * @param name the variables name
     * @param index the variables index
     */
    public Variable(String name, Integer index) {
        this.name = name;
        this.index = Optional.of(Integer.valueOf(index));
    }

    /**
     * @return the variables name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the variables optional index
     */
    public Optional<Integer> getIndex() {
        return this.index;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Variable transform(TermTransformationVisitor visitor) {
        return visitor.visit(this);
    }

    /**
     * @return evaluates the variable arithmetically (always throws an
     * UnsetVariableException, since trying to evaluate a variable means that it
     * hasnt been set)
     * @throws UnsetVariableException whenever this method is called
     */
    public Number evaluate() throws UnsetVariableException {
        throw new UnsetVariableException(this);
    }

    /**
     * @return a string representation of this variable
     */
    public String toString() {
        return this.getName() + (this.getIndex().isPresent() ? String.format("/%d", this.getIndex().get()) : "");
    }

    /**
     * @return a GraphViz-compatible HTML representation of this variable
     */
    public String toHtml() {
        return this.getName()
                + (this.getIndex().isPresent() ? String.format("<sub>%d</sub>", this.getIndex().get()) : "");
    }

    /**
     * Checks whether this is equal to another Object. Only returns true if the
     * other is Object is a variable of the same name.
     *
     * @param other other Object
     * @return whether this equals other according to the rules above
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(Variable.class)) {
            return false;
        }

        Variable otherVar = (Variable) other;

        return otherVar.getName().equals(this.getName()) && otherVar.getIndex().equals(this.getIndex());
    }
}
