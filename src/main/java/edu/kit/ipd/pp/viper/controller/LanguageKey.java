package edu.kit.ipd.pp.viper.controller;

/**
 * Enumeration of key values that can be used to access the proper translation
 * of a String in the GUI.
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
     * Menu "Save as..." option
     */
    MENU_SAVEAS("menu_saveas"),

    /**
     * Menu "Recently used" option
     */
    MENU_RECENT("menu_recent"),

    /**
     * Menu "Sample programs" option
     */
    MENU_SAMPLES("menu_samples"),

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
    
    /**
     * Menu "Reset text zoom" option
     */
    MENU_RESET_TEXT_ZOOM("menu_reset_text_zoom"),
    
    /**
     * Menu "Reset visualisation zoom" option
     */
    MENU_RESET_VISUALISATION_ZOOM("menu_reset_visualisation_zoom"),    
    
    /**
     * Menu "Help" option
     */
    MENU_HELP("menu_help"),
    
    /**
     * Menu "About" option
     */
    MENU_ABOUT("menu_about"),
    
    /**
     * Menu "Show standard library" option
     */
    MENU_SHOW_STANDARD("menu_show_standard"),
    
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
    TOOLTIP_PREVIOUSSTEP("tooltip_previousstep"),

    /**
     * Tool bar "Next Step" tool tip
     */
    TOOLTIP_NEXTSTEP("tooltip_nextstep"),

    /**
     * Tool bar "Next Solution" tool tip
     */
    TOOLTIP_NEXTSOLUTION("tooltip_nextsolution"),

    /**
     * Tool bar "Finish Query" tool tip
     */
    TOOLTIP_FINISHQUERY("tooltip_finishquery"),

    /**
     * Tool bar "Cancel" tool tip
     */
    TOOLTIP_CANCEL("tooltip_cancel"),

    /**
     * Tool bar "Zoom in" tool tip
     */
    TOOLTIP_ZOOM_IN("tooltip_zoom_in"),
    
    /**
     * Tool bar "Zoom out" tool tip
     */
    TOOLTIP_ZOOM_OUT("tooltip_zoom_out"),

    /**
     * Zoom target: visualisation
     */
    ZOOM_TARGET_VIS("zoom_target_visualisation"),

    /**
     * Zoom target: editor
     */
    ZOOM_TARGET_ED("zoom_target_editor"),

    /**
     * Zoom target: console
     */
    ZOOM_TARGET_CO("zoom_target_console"),
    
    /**
     * Zoom target: editor & console
     */
    ZOOM_TARGET_EDCO("zoom_target_editor_console"),
    
    /**
     * Zoom target: all
     */
    ZOOM_TARGET_ALL("zoom_target_all"),
    
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
    DIALOG_YES("dialog_yes"),

    /**
     * "No" dialog option
     */
    DIALOG_NO("dialog_no"),

    /**
     * "Cancel" dialog option
     */
    DIALOG_CANCEL("dialog_cancel"),

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
     * Title of the exit confirmation dialog asking whether unsaved changes should
     * be saved to disk
     */
    CONFIRMATION_CLOSE_TITLE("confirm_close_title"),

    /**
     * Hint text displayed when the console input field is empty
     */
    INPUT_HINT("input_hint"),
    
    /**
     * Hint text displayed when the visualisation doesn't have a graph
     */
    VISUALISATION_HINT("visualisation_hint"),
    
    /**
     * Name of the project displayed in the about popup
     */
    ABOUT_NAME("about_name"),

    /**
     * Name of the project authors displayed in the about popup
     */
    ABOUT_AUTHORS("about_authors"),
    
    /**
     * Name of the used libraries displayed in the about popup
     */
    ABOUT_LIBRARIES("about_libraries"),
    
    /**
     * Project description displayed in the about popup
     */
    ABOUT_DESCRIPTION("about_description"),
    
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
     * Console output message for conflicting rules in given program and standard library
     */
    CONFLICTING_RULES("conflicting_rules"),

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
     * Note that describes what happens when you backtrack into a cut
     */
    VISUALISATION_CUT_NOTE("visualisation_cut_note"),

    /**
     * Note that describes what happens when you backtrack into a cut without a parent
     */
    VISUALISATION_CUT_ABORT("visualisation_cut_abort"),

    /**
     * Line containing the error for an ParseException
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
     * "Expected list" warning.
     */
    EXPECTED_LIST("expected_list"),

    /**
     * "Expected list remainder" warning.
     */
    EXPECTED_LIST_REST("expected_list_rest"),

    /**
     * The Goal not supported error for parser
     */
    GOAL_NOT_SUPPORTED("goal_not_supported"),
    
    /**
     * The output position if the parser fails due to the
     * first character
     */
    FIRST_CHARACTER("first_character"),
    
    /**
     * The no knowledge base present error for the interpreter manager
     */
    NO_KNOWLEDGEBASE_PRESENT("no_knowledgebase_present"),

    /**
     * The Term translation
     */
    TERM("term"),

    /**
     * The Functor translation
     */
    FUNCTOR("functor"),

    /**
     * Translation of unification for visualisation.
     */
    UNIFICATION("unification"),
    
    /**
     * Translation of standard library
     */
    STANDARD_LIBRARY("standard_library"),
    
    /**
     * Manual window title and menu item
     */
    MANUAL("manual"),

    /**
     * Manual tab of the manual window
     */
    TAB_MANUAL("tab_manual"),

    /**
     * Controls tab of the manual window
     */
    TAB_CONTROLS("tab_controls"),
    
    /**
     * Manual text of the manual window
     */
    MANUAL_MANUAL("manual_manual"),
    
    /**
     * Controls text of the manual window
     */
    MANUAL_CONTROLS("manual_controls");

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
