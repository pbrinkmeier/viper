package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

/**
 * Occurs when an unsupported term is evaluated arithmetically.
 * For example, functors carry no arithmetic meaning and throw this exception when evaluated.
 */
public class UnsupportedTermException extends TermEvaluationException {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 6776710547273320120L;

    private final Term term;

    /**
     * Initializes an exception with an unsupported term.
     *
     * @param term term causing the exception
     */
    public UnsupportedTermException(Term term) {
        this.term = term;
    }

    /**
     * Returns an error message in the currently selected language telling the user
     * that a certain term can not be evaluated arithmetically.
     *
     * @return error message in the currently selected language
     */
    public String getMessage() {
        return String.format(
            LanguageManager.getInstance().getString(LanguageKey.ARITHMETIC_UNSUPPORTED_TERM),
            this.term
        );
    }
}
