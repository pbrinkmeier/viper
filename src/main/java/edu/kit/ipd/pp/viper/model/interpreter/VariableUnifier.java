package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.Arrays;

public class VariableUnifier extends Unifier<Variable> {
    /**
     * Initializes a variable unifier with a variable.
     *
     * @param variable variable to create substitutions with
     */
    public VariableUnifier(Variable variable) {
        super(variable);
    }

    /**
     * Creates a result that substitutes the variable of this unifier with the given term.
     *
     * @param term term to subtstitute with
     * @return a success-result with a single substitution 
     */
    private UnificationResult createResult(Term term) {
        return UnificationResult.success(Arrays.asList(new Substitution(this.getTerm(), term)));
    }

    /**
     * Returns a substitution as described in {@link #createResult(Term)}.
     *
     * @param functor functor to substitute with
     * @return a success-result with a single substitution
     */
    public UnificationResult visit(Functor functor) {
        return this.createResult(functor);
    }

    /**
     * Returns a substitution as described in {@link #createResult(Term)}.
     *
     * @param variable variable to substitute with
     * @return a success-result with a single substitution
     */
    public UnificationResult visit(Variable variable) {
        return this.createResult(variable);
    }

    /**
     * Returns a substitution as described in {@link #createResult(Term)}.
     *
     * @param number number to substitute with
     * @return a success-result with a single substitution
     */
    public UnificationResult visit(Number number) {
        return this.createResult(number);
    }
}
