package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

import java.util.ArrayList;
import java.util.List;

public class SubstitutionApplier implements TermVisitor<Term> {
    private Substitution substitution;

    /**
     * Initializes a substitution applier with the substitution to apply.
     *
     * @param substitution substitution to apply
     */
    public SubstitutionApplier(Substitution substitution) {
        this.substitution = substitution;
    }

    /**
     * Getter-method for the substitution to apply.
     * @return substitution to apply
     */
    private Substitution getSubstitution() {
        return this.substitution;
    }

    /**
     * How to apply a substitution to a functor.
     * Apply the substitution to its parameters and leave the name.
     *
     * @param functor functor to apply a substitution to
     * @return same functor, with the substitution applied to its parameters
     */
    @Override
    public Term visit(Functor functor) {
        List<Term> parameters = new ArrayList<>();

        for (Term parameter : functor.getParameters()) {
            parameters.add(parameter.accept(this));
        }

        return functor.createNew(parameters);
    }

    /**
     * How to apply a substitution to a variable.
     * If the substitutions replace field is the same as the variable,
     * replace it by the substitutions by field.
     * Else, leave it.
     *
     * @param variable variable term to replace
     * @return if no substitution occurred the variable, else its replacement
     */
    @Override
    public Term visit(Variable variable) {
        if (this.getSubstitution().getReplace().equals(variable)) {
            return this.getSubstitution().getBy();
        }

        return variable;
    }

    /**
     * How to apply a substitution to a number.
     * A number just stays the same; it is never substituted.
     *
     * @param number number term to visit for substitution
     * @return just the same number term
     */
    @Override
    public Term visit(Number number) {
        return number;
    }
}
