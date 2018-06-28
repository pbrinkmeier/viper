package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

public final class Variable extends Term {
    private final String name;
    private final Optional<Integer> index;

    public Variable(String name) {
        this.name = name;
        this.index = Optional.empty();
    }

    /**
     * @param name 
     * @param index
     */
    public Variable(String name, Integer index) {
        this.name = name;
        this.index = Optional.of(Integer.valueOf(index));
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return
     */
    public Optional<Integer> getIndex() {
        return this.index;
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
     * @return
     */
    public Number evaluate() throws TermEvaluationException {
        throw new UnsetVariableException(this);
    }

    /**
     * @return
     */
    public String toString() {
        return this.getName() + (this.getIndex().isPresent() ? String.format("/%d", this.getIndex().get()) : "");
    }

    /**
     * @return
     */
    public String toHtml() {
        // TODO
        return null;
    }
}
