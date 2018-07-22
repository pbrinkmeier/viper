package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * Arithmetic evaluation exception that occurs when a variable has not been set during evaluation.
 * Variables always need to be substituted before evaluation.
 */
public class UnsetVariableException extends TermEvaluationException {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -9083832447258935567L;

    private final Variable var;

    /**
     * Intitializes exception with the unset variable.
     *
     * @param var variable that has not been set
     */
    public UnsetVariableException(Variable var) {
        this.var = var;
    }

    /**
     * Returns a message in the currently selected language that tells the user that
     * the variable passed in the constructor has not been set.
     *
     * @return a message in the currently selected language
     */
    @Override
    public String getMessage() {
        return String.format(
            LanguageManager.getInstance().getString(LanguageKey.ARITHMETIC_UNSET_VARIABLE),
            this.var.toString()
        );
    }
}
