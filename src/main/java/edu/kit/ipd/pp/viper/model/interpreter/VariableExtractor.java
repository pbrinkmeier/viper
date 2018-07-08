package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariableExtractor implements TermVisitor<List<Variable>> {
    /**
     * Extracts variables from a functor.
     * Loops through parameters and extracts their variables.
     *
     * @param functor functor to extract from
     * @return a list of variables that have been extracted
     */
    @Override
    public List<Variable> visit(Functor functor) {
        List<Variable> variables = new ArrayList<>();

        for (Term parameter : functor.getParameters()) {
            // this should actually use a Set, not a list.
            // But we dont have a hashCode on Term/Variable yet :(
            for (Variable variable : parameter.accept(this)) {
                if (!variables.contains(variable)) {
                    variables.add(variable);
                }
            }
        }

        return variables;
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
        return Arrays.asList(variable);
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
        return Arrays.asList();
    }
}
