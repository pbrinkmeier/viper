package edu.kit.ipd.pp.viper.controller;

/**
 * Enumeration of key values that can be used to access the proper
 * translation of a String in the GUI.
 */
public enum LanguageKey {
    // Menu
    /**
     * Menu "File" option
     */
    MENU_FILE("menu_file"),

    /**
     * Menu "New" option
     */
    MENU_NEW("menu_new"),

    /**
     * Menu "Open" option
     */
    MENU_OPEN("menu_open"),

    /**
     * Menu "Save" option
     */
    MENU_SAVE("menu_save"),

    /**
     * Menu "Save As..." option
     */
    MENU_SAVEAS("menu_saveas"),

    /**
     * Menu "Recent" option
     */
    MENU_RECENT("menu_recent"),

    /**
     * Menu "Exit" option
     */
    MENU_EXIT("menu_exit"),

    /**
     * Menu "Program" option
     */
    MENU_PROGRAM("menu_program"),

    /**
     * Menu "Parse" option
     */
    MENU_PARSE("menu_parse"),

    /**
     * Menu "Format" option
     */
    MENU_FORMAT("menu_format"),

    /**
     * Menu "Export" option
     */
    MENU_EXPORT("menu_export"),

    /**
     * Menu "Export as PNG" option
     */
    MENU_EXPORT_PNG("menu_export_png"),

    /**
     * Menu "Export as SVG" option
     */
    MENU_EXPORT_SVG("menu_export_svg"),

    /**
     * Menu "Export as TikZ" option
     */
    MENU_EXPORT_TIKZ("menu_export_tikz"),

    /**
     * Menu "Settings" option
     */
    MENU_SETTINGS("menu_settings"),

    /**
     * Menu "Toggle Standard Library" option
     */
    MENU_STDLIB("menu_stdlib"),

    /**
     * Menu "Language" option
     */
    MENU_LANGUAGE("menu_language"),

    // Tool tips
    /**
     * Tool bar "New" tool tip
     */
    TOOLTIP_NEW("tooltip_new"),

    /**
     * Tool bar "Open" tool tip
     */
    TOOLTIP_OPEN("tooltip_open"),

    /**
     * Tool bar "Save" tool tip
     */
    TOOLTIP_SAVE("tooltip_save"),

    /**
     * Tool bar "Parse" tool tip
     */
    TOOLTIP_PARSE("tooltip_parse"),

    /**
     * Tool bar "Format" tool tip
     */
    TOOLTIP_FORMAT("tooltip_format"),

    /**
     * Tool bar "Previous step" tool tip
     */
    TOOLTIP_STEPBACK("tooltip_stepback"),

    /**
     * Tool bar "Next Step" tool tip
     */
    TOOLTIP_STEP("tooltip_step"),

    /**
     * Tool bar "Next Solution" tool tip
     */
    TOOLTIP_NEXT("tooltip_next"),

    /**
     * Tool bar "Cancel" tool tip
     */
    TOOLTIP_CANCEL("tooltip_cancel"),

    /**
     * Tooltip for zoom in button
     */
    ZOOM_IN("zoom_in"),

    /**
     * Tooltip for zoom in button
     */
    ZOOM_OUT("zoom_out"),

    // Buttons
    /**
     * Text for "send" button next to input field
     */
    BUTTON_SEND("button_send"),

    // Dialog options
    /**
     * "Yes" dialog option
     */
    YES("yes"),

    /**
     * "No" dialog option
     */
    NO("no"),

    /**
     * "Cancel" dialog option
     */
    CANCEL("cancel"),

    /**
     * Message of the new file confirmation dialog asking whether unsaved changes
     * should be saved to disk
     */
    CONFIRMATION("confirm_new"),

    /**
     * Title of the new file confirmation dialog asking whether unsaved changes
     * should be saved to disk
     */
    CONFIRMATION_NEW_TITLE("confirm_new_title"),

    /**
     * Title of the exit confirmation dialog asking whether unsaved changes
     * should be saved to disk
     */
    CONFIRMATION_CLOSE_TITLE("confirm_close_title"),

    // File types
    /**
     * PNG files export dialog filter description
     */
    PNG_FILES("png_files"),

    /**
     * SVG files export dialog filter description
     */
    SVG_FILES("svg_files"),

    /**
     * TikZ files export dialog filter description
     */
    TIKZ_FILES("tikz_files"),

    /**
     * Prolog files save dialog filter description
     */
    PROLOG_FILES("prolog_files"),

    // Console output on basic IO operations
    /**
     * Console output message on successful opening of a file
     */
    OPEN_FILE_SUCCESS("open_file_success"),

    /**
     * Console output message on successful saving of a file
     */
    SAVE_FILE_SUCCESS("save_file_success"),

    /**
     * Console output message on failed opening of a file
     */
    OPEN_FILE_ERROR("open_file_error"),

    /**
     * Console output message on failed saving of a file
     */
    SAVE_FILE_ERROR("save_file_error"),

    // Console output on export operations
    /**
     * Console output message on successful export of a file
     */
    EXPORT_FILE_SUCCESS("export_file_success"),

    /**
     * Console output message on failed export of a file
     */
    EXPORT_FILE_ERROR("export_file_error"),

    /**
     * Console output message on failed export due to unsupported format
     */
    EXPORT_UNSUPPORTED_FORMAT("export_unsupported_format"),

    // Console output for query results
    /**
     * Console output message for a possible solution
     */
    SOLUTION_FOUND("solution_found"),

    /**
     * Console output message for successful queries without variables
     */
    SOLUTION_YES("solution_yes"),

    /**
     * Console output message when the end was reached
     */
    NO_MORE_SOLUTIONS("no_more_solutions"),

    // Console output for editor changes
    /**
     * Console output message for formatting the editor content
     */
    EDITOR_FORMATTED("editor_formatted"),

    /**
     * Console output message for editor content is already properly formatted
     */
    EDITOR_ALREADY_FORMATTED("editor_already_formatted"),

    // Console output for parsing
    /**
     * Console output message for failed parsing
     */
    PARSER_SUCCESS("parser_success"),

    /**
     * Console output message for failed parsing
     */
    PARSER_ERROR("parser_error"),

    /**
     * Console output message for failed query parsing
     */
    PARSER_QUERY_ERROR("parser_query_error"),

    /**
     * Console output for when the visualisation has been started
     */
    VISUALISATION_STARTED("visualisation_started"),

    /**
     * Arithmetic evaluation error for unset variables.
     */
    ARITHMETIC_UNSET_VARIABLE("arithmetic_unset_variable"),

    /**
     * Arithmetic evaluation error for unsupported terms.
     */
    ARITHMETIC_UNSUPPORTED_TERM("arithmetic_unsupported_term"),

    /**
     * Console output when program starts
     */
    VIPER_READY("viper_started"),

    /**
     * Line containing the error for an ParserException
     */
    POSITION("position"),

    /**
     * Illegal Character Warning for Parser Failures
     */
    ILLEGAL_CHAR("illegal_char"),

    /**
     * "Operator not recognized" warning for parser failures
     */
    OPERATOR_NOT_RECOGNIZED("operator_not_recognized"),

    /**
     * "Expected * instead of" warnings for parser failures
     */
    EXPECTED_INSTEAD("expected_instead"),

    /**
     * Expected goalrest (i.e. =, is or arithmetic comparison)
     */
    EXPECTED_GOALREST("expected_goalrest"),

    /**
     * The Goal not supported error for parser
     */
    GOAL_NOT_SUPPORTED("goal_not_supported"),

    /**
     * The Term translation
     */
    TERM("term"),

    /**
     * The Functor translation
     */
    FUNCTOR("functor"),

    /** Lists unsupported error for parser */
    UNSUPPORTED_LISTS("unsupported_lists"),

    /**
     * Translation of unification for visualisation.
     */
    UNIFICATION("unification");

    private String value;

    private LanguageKey(String value) {
        this.value = value;
    }

    /**
     * Getter-Method for the string value of an enum.
     * 
     * @return string value of a specific enum
     */
    public String getString() {
        return this.value;
    }
}
