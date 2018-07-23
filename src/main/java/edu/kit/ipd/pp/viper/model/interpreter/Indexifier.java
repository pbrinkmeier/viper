package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.TermTransformationVisitor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that gives every variable in a term an index.
 */
public class Indexifier implements TermTransformationVisitor {
    private final int index;

    /**
     * Initializes an indexifier with the index to give to variables.
     *
     * @param index index this indexifier will give variables
     */
    public Indexifier(int index) {
        this.index = index;
    }

    // ---

    @Override
    public Functor visit(Functor functor) {
        List<Term> newParameters = new ArrayList<>();

        for (Term oldParameter : functor.getParameters()) {
            newParameters.add(oldParameter.transform(this));
        }

        return functor.createNew(newParameters);
    }

    @Override
    public Variable visit(Variable variable) {
        // exisiting indices are ignored
        return new Variable(variable.getName(), this.index);
    }

    @Override
    public Number visit(Number number) {
        return number;
    }
}
