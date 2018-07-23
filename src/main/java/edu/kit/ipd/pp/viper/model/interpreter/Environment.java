package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import edu.kit.ipd.pp.viper.model.ast.Term;

/**
 * Environment of an activation record.
 * Consists of a reference to its activation record and a list of substitutions.
 */
public class Environment {
    private final ActivationRecord activationRecord;
    private final List<Substitution> substitutions;

    /**
     * Initializes an environment with an activation record and a list of
     * substitutions. This class is immutable.
     *
     * @param activationRecord activation record this environment belongs to
     * @param substitutions local substitutions this environment stores for the
     *        activation record
     */
    public Environment(ActivationRecord activationRecord, List<Substitution> substitutions) {
        this.activationRecord = activationRecord;
        this.substitutions = Collections.unmodifiableList(substitutions);
    }

    /**
     * Getter-method for the local substitutions. The returned list is immutable.
     *
     * @return local substitutions this environment stores for the activation record
     */
    public List<Substitution> getSubstitutions() {
        return this.substitutions;
    }

    /**
     * Applies substitutions from all previous activation records and substitutions
     * from this environment.
     *
     * @param term term to apply the substitutions in
     * @return new term with all substitutions applied
     */
    public Term applyAllSubstitutions(Term term) {
        Optional<ActivationRecord> previous = this.activationRecord.getPrevious();

        Term newTerm = term;

        if (previous.isPresent()) {
            newTerm = previous.get().getEnvironment().applyAllSubstitutions(newTerm);
        }

        for (Substitution s : this.getSubstitutions()) {
            newTerm = newTerm.accept(new SubstitutionApplier(s));
        }

        return newTerm;
    }
}
