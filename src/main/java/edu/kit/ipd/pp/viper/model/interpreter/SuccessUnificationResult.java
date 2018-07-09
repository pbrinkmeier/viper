package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.List;

class SuccessUnificationResult extends UnificationResult {
    private final List<Substitution> substitutions;

    /**
     * Initializes a success-result with a list of substitutions.
     *
     * @param substitutions substitutions that were a result of an unification
     */
    public SuccessUnificationResult(List<Substitution> substitutions) {
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
        String repr = "";

        for (int index = 0; index < this.substitutions.size(); index++) {
            repr +=
                String.format("%s ⇒ %s",
                    this.substitutions.get(index).getReplace().toHtml(),
                    this.substitutions.get(index).getBy().toHtml()
                );

            if (index != this.substitutions.size() - 1) {
                repr += ", ";
            }
        }

        return repr;
    }
}
