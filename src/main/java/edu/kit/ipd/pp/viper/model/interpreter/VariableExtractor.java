package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.List;

public class VariableExtractor implements TermVisitor<List<Variable>> {
    /**
     * Initializes a variable extractor.
     * Creates an empty list internally to keep track of the variables found.
     */
    public VariableExtractor() {
        // TODO
    }

    /**
     * Extracts variables from a functor.
     * Loops through parameters and extracts their variables.
     *
     * @param functor functor to extract from
     * @return a list of variables that have been extracted
     */
    @Override
    public List<Variable> visit(Functor functor) {
        // TODO
        return null;
    }

    /**
     * Extracts "all" variables from a variable.
     * This means that it just returns that variable.
     *
     * @param variable variable to extract from
     * @return a list with the visited variable as the single entry
     */
    @Override
    public List<Variable> visit(Variable variable) {
        // TODO
        return null;
    }

    /**
     * Extracts a variable from a number.
     * Obviously there are no variables in a number, so this
     * method always return an empty list.
     *
     * @param number to visit
     * @return an empty list
     */
    @Override
    public List<Variable> visit(Number number) {
        // TODO
        return null;
    }
}
