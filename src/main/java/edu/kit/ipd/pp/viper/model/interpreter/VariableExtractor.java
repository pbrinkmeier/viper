package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Extracts all variables from a term.
 * For example, this is used to determine what variables to solve for in a query.
 */
public class VariableExtractor implements TermVisitor<Set<Variable>> {
    /**
     * Extracts variables from a functor. Loops through parameters and extracts
     * their variables.
     *
     * @param functor functor to extract from
     * @return a set of variables that have been extracted
     */
    @Override
    public Set<Variable> visit(Functor functor) {
        Set<Variable> variables = new HashSet<>();

        for (Term parameter : functor.getParameters()) {
            variables.addAll(parameter.accept(this));
        }

        return variables;
    }

    /**
     * Extracts "all" variables from a variable. This means that it just returns
     * that variable.
     *
     * @param variable variable to extract from
     * @return a set with the visited variable as the single entry
     */
    @Override
    public Set<Variable> visit(Variable variable) {
        return Collections.singleton(variable);
    }

    /**
     * Extracts a variable from a number. Obviously there are no variables in a
     * number, so this method always return an empty list.
     *
     * @param number to visit
     * @return an empty list
     */
    @Override
    public Set<Variable> visit(Number number) {
        return Collections.EMPTY_SET;
    }
}
