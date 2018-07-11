package edu.kit.ipd.pp.viper.view;

/**
 * Enumeration of possible states affecting clickable GUI elements like buttons
 * or menu items.
 */
public enum ClickableState {
    /** State in which no code has been parsed yet */
    NOT_PARSED_YET,
    /** State in which program code has been parsed but no query has been sent */
    PARSED_PROGRAM,
    /** State in which program code has been parsed and a query has been sent*/
    PARSED_QUERY
}
