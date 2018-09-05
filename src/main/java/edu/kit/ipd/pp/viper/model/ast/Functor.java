package edu.kit.ipd.pp.viper.model.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 * Represents a functor in an AST. A functor has a "name" with is just a string
 * and a list of terms which are called its "parameters".
 * 
 * A thing to note about functors is that {@link BinaryOperation}s behave like
 * functors with exactly two parameters in most cases, which is why the
 * {@link TermVisitor} and {@link TermTransformationVisitor} interfaces do not
 * have special "visit" methods for them. This leads to the following peculiar
 * situation: when visiting a BinaryOperation with an Unifier or any kind of
 * {@link TermTransformationVisitor}, we will need to create a new instance with
 * the modified parameters. But even though we are visiting a functor object, we
 * can't just create a new functor, since we would lose the information that the
 * visited object was an arithmetic operation. This is why the Functor class
 * provides the createNew method, which is used to create a new instance of the
 * exact same class. Subclasses of BinaryOperation are forced to override this
 * method in order to create an instance of the correct class.
 */
public class Functor extends Term {
    private final String name;
    private final List<Term> parameters;

    /**
     * Initializes a functor with a name and a list of parameters. This class is
     * immutable.
     *
     * @param name name of the functor
     * @param parameters parameters of the functor
     */
    public Functor(String name, List<Term> parameters) {
        super();

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
     * Checks whether a functor has the same head and arity as another functor.
     * This method is used for determining similar (a.k.a. matching) functors in a variety of contexts.
     * An example is the FunctorActivationRecord which uses this method (through KnowledgeBase) to find
     * rules that may be unified with its functor.
     *
     * @param otherFunctor functor to compare to
     * @return whether the other functor matches this functor
     */
    public boolean matches(Functor otherFunctor) {
        return this.name.equals(otherFunctor.name) && this.getArity() == otherFunctor.getArity();
    }

    /**
     * Creates a new functor with different parameters. This method is supposed to
     * be overwritten in subclasses for use in Unifiers and
     * TermTransformationVisitors, which only have a visit(Functor) method.
     *
     * @param parameters parameters of the new functor
     * @return a newly created functor of exactly the same class as this one
     */
    public Functor createNew(List<Term> parameters) {
        return new Functor(this.getName(), parameters);
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Functor transform(TermTransformationVisitor visitor) {
        return visitor.visit(this);
    }

    /**
     * As functors can not be arithmetically evaluated, this method just throws an
     * Exception. This method will be overridden by the *Operation subclasses.
     * Although this implementation always throws an UnsupportedOperationException,
     * overwriting methods in subclasses may return any kind of
     * TermEvaluationException; thus, only that Exception can be declared to be
     * thrown here.
     *
     * @return the result of evaluating this term; in the case of a functor, there
     *         will never be any result
     */
    @Override
    public Number evaluate() throws TermEvaluationException {
        throw new UnsupportedTermException(this);
    }

    /**
     * @return a string representation of this functor
     */
    @Override
    public String toString() {
        if (this.name.equals("[|]") && this.getArity() == 2) {
            return ListFormatter.asString(this)
                    .orElse(String.format("[%s | %s]", this.parameters.get(0), this.parameters.get(1)));
        }

        StringBuilder builder = new StringBuilder(this.name);

        if (this.getArity() > 0) {
            builder.append("(");
            builder.append(this.parameters.stream().map(parameter -> parameter.toString()).collect(joining(", ")));
            builder.append(")");
        }

        return builder.toString();
    }

    /**
     * Getter-method for the short notation for functors (e.g. functor/3).
     *
     * @return short string representation of the functor
     */
    public String getShortNotation() {
        return String.format("%s/%d", this.name, this.getArity());
    }

    /**
     * @return a GraphViz-compatible HTML representation of this functor
     */
    @Override
    public String toHtml() {
        if (this.name.equals("[|]") && this.getArity() == 2) {
            return ListFormatter.asHtml(this).orElse(
                    String.format("[%s &#124; %s]", this.parameters.get(0).toHtml(), this.parameters.get(1).toHtml()));
        }

        StringBuilder builder = new StringBuilder(this.getName());

        if (this.getArity() > 0) {
            builder.append("(");
            builder.append(this.getParameters().stream().map(parameter -> parameter.toHtml()).collect(joining(", ")));
            builder.append(")");
        }

        return builder.toString();
    }

    /**
     * Checks whether this equals another object. Will only return true for functors
     * of the same name and parameters.
     *
     * @param other other Object
     * @return whether this is equal to object according to the rules defined above
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(Functor.class)) {
            return false;
        }

        Functor otherFunctor = (Functor) other;

        if (!otherFunctor.getName().equals(this.getName())) {
            return false;
        }

        return otherFunctor.getParameters().equals(this.getParameters());
    }

    /**
     * Creates a functor without any parameters, also called an atom.
     *
     * @param name name of the atom
     * @return functor instance with a name but no parameters
     */
    public static Functor atom(String name) {
        return new Functor(name, Arrays.asList());
    }
}
