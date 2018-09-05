package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.List;

/**
 * Represents a successful unification.
 * A successful unification yields a list of substitutions.
 */
class SuccessUnificationResult extends UnificationResult {
    private final List<Substitution> substitutions;

    /**
     * Initializes a success-result with a list of substitutions.
     *
     * @param substitutions substitutions that were a result of an unification
     */
    public SuccessUnificationResult(List<Substitution> substitutions) {
        super();

        this.substitutions = substitutions;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public List<Substitution> getSubstitutions() {
        return this.substitutions;
    }

    @Override
    public String getErrorMessage() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toHtml() {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < this.substitutions.size(); index++) {
            builder.append(String.format("%s &#x21d2; %s", this.substitutions.get(index).getReplace().toHtml(),
                    this.substitutions.get(index).getBy().toHtml()));

            if (index != this.substitutions.size() - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }
}
