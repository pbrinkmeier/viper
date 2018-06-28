package edu.kit.ipd.pp.viper.model.ast;

import java.util.Collections;
import java.util.List;

public class Functor extends Term {
    private final String name;
    private final List<Term> parameters;

    /**
     * Initializes a functor with a name and a list of parameters.
     * This class is immutable.
     *
     * @param name name of the functor
     * @param parameters parameters of the functor
     */
    public Functor(String name, List<Term> parameters) {
        this.name = name;
        this.parameters = Collections.unmodifiableList(parameters);
    }

    // --- getters and setters

    /**
     * Getter-method for this functors name.
     *
     * @return name of the functor
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for this functors parameters.
     *
     * @return parameters of the functor (immutable)
     */
    public List<Term> getParameters() {
        return this.parameters;
    }

    /**
     * Getter-method for this functors arity.
     *
     * @return arity of the functor (i.e. the number of parameters)
     */
    public int getArity() {
        return this.getParameters().size();
    }

    // --- functionality

    /**
     * Creates a new functor. This method is supposed to be overwritten in subclasses for use in TermTransformationVisitors, which only have a visit(Functor) method.
     *
     * @param name name of the new functor
     * @param parameters parameters of the new functor
     * @return a newly created functor of exactly same class as this one
     */
    public Functor createNew(String name, List<Term> parameters) {
        return new Functor(name, parameters);
    }

    /**
     * Interface for TermVisitors.
     *
     * @param visitor visitor to visit this functor
     * @return result of the visit
     */
    @Override
    public <ResultType> ResultType accept(TermVisitor<ResultType> visitor) {
        return visitor.visit(this);
    }

    /**
     * As functors can not be arithmetically evaluated, this method just throws an Exception.
     * This method will be overridden by the *Operation subclasses.
     *
     * @return the result of evaluating this term; in the case of a functor, there will never be any result
     */
    @Override
    public Number evaluate() throws UnsupportedTermException {
        throw new UnsupportedTermException(this);
    }

    /**
     * @return a string representation of this functor
     */
    @Override
    public String toString() {
        String repr = this.getName();

        if (this.getArity() > 0) {
            repr += "(";
            for (int index = 0; index < this.getArity(); index++) {
                repr += this.getParameters().get(index).toString();
                
                if (index != this.getArity() - 1) {
                    repr += ", ";
                }
            }

            repr += ")";
        }

        return repr;
    }



    /**
     * @return a GraphViz-compatible HTML representation of this functor
     */
    public String toHtml() {
        return this.toString();
    }
}
