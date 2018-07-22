package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;

public class VariableUnifier extends Unifier {
    private final Variable variable;

    /**
     * Initializes a variable unifier with a variable.
     *
     * @param variable variable to create substitutions with
     */
    public VariableUnifier(Variable variable) {
        this.variable = variable;
    }

    @Override
    protected Variable getTerm() {
        return this.variable;
    }

    /**
     * Returns a substitution as described in {@link Unifier#createSubstitution(Variable, Term)}.
     *
     * @param functor functor to substitute with
     * @return a success-result with a single substitution
     */
    public UnificationResult visit(Functor functor) {
        return this.createSubstitution(this.getTerm(), functor);
    }

    /**
     * Returns a result in which the visited variable is substituted by this
     * unifiers variable. This means that the left side of any unification is
     * "stronger" than the right side. The variables on the left side persist.
     *
     * @param variable variable substitute
     * @return a success-result with a single substitution
     */
    public UnificationResult visit(Variable variable) {
        return this.createSubstitution(variable, this.getTerm());
    }

    /**
     * Returns a substitution as described in {@link Unifier#createSubstitution(Variable, Term)}.
     *
     * @param number number to substitute with
     * @return a success-result with a single substitution
     */
    public UnificationResult visit(Number number) {
        return this.createSubstitution(this.getTerm(), number);
    }
}
