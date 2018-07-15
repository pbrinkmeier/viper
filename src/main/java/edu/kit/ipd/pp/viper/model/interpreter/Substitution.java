package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;

public class Substitution {
    private final Variable replace;
    private final Term by;

    /**
     * Initializes a substitution with a variable to replace and a term to replace
     * it with. This class is immutable.
     *
     * @param replace variable to replace
     * @param by term to replace the variable by
     */
    public Substitution(Variable replace, Term by) {
        this.replace = replace;
        this.by = by;
    }

    /**
     * Getter-method for the variable to replace.
     *
     * @return variable to replace
     */
    public Variable getReplace() {
        return this.replace;
    }

    /**
     * Getter method for the term to replace the variable by.
     *
     * @return term to replace the variable by
     */
    public Term getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return String.format("%s => %s", this.replace, this.by);
    }

    /**
     * Checks whether a substitution is equal to another object. Two substitutions
     * are equal, if their replace and by fields are equal.
     *
     * @param other Object to check equality to
     * @return whether this is equal to other
     */
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(Substitution.class)) {
            return false;
        }

        Substitution otherSubst = (Substitution) other;

        return otherSubst.getReplace().equals(this.getReplace()) && otherSubst.getBy().equals(this.getBy());
    }
}
