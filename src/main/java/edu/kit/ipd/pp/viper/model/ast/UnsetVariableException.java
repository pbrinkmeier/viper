package edu.kit.ipd.pp.viper.model.ast;

public class UnsetVariableException extends TermEvaluationException {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -9083832447258935567L;

    /**
     * @param var variable that has not been set
     */
    public UnsetVariableException(Variable var) {
        // TODO
    }

    /**
     * Returns a message in the currently selected language
     * that tells the user that the variable passed in the
     * constructor has not been set.
     *
     * @return a message in the currently selected language
     */
    public String getMessage() {
        // TODO
        return null;
    }
}
