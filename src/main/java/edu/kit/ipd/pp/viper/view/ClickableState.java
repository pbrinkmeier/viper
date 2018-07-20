package edu.kit.ipd.pp.viper.view;

/**
 * Enumeration of possible states affecting clickable GUI elements like buttons
 * or menu items.
 */
public enum ClickableState {
    /**
     * State in which no code has been parsed yet
     */
    NOT_PARSED_YET,

    /**
     * State in which program code has been parsed but no query has been sent
     */
    PARSED_PROGRAM,

    /**
     * State in which program code has been parsed and a query has been sent
     */
    PARSED_QUERY,

    /**
     * State when the interpreter is in the first step
     */
    FIRST_STEP,

    /**
     * State when the interpreter is in the last step
     */
    LAST_STEP,

    /**
     * State in which there are no more solutions to a query
     */
    NO_MORE_SOLUTIONS
}
