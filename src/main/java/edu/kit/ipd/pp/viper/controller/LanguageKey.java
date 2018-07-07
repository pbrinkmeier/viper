package edu.kit.ipd.pp.viper.controller;

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
     * Menu "Recent >" option
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

    // Tooltips
    /**
     * Toolbar "New" tooltip
     */
    TOOLTIP_NEW("tooltip_new"),

    /**
     * Toolbar "Open" tooltip
     */
    TOOLTIP_OPEN("tooltip_open"),

    /**
     * Toolbar "Save" tooltip
     */
    TOOLTIP_SAVE("tooltip_save"),

    /**
     * Toolbar "Parse" tooltip
     */
    TOOLTIP_PARSE("tooltip_parse"),

    /**
     * Toolbar "Format" tooltip
     */
    TOOLTIP_FORMAT("tooltip_format"),

    /**
     * Toolbar "Next Step" tooltip
     */
    TOOLTIP_STEP("tooltip_step"),

    /**
     * Toolbar "Next Solution" tooltip
     */
    TOOLTIP_NEXT("tooltip_next"),

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
    CONFIRMATION_TITLE("confirm_new_title"),

    // File types
    /**
     * Image files export dialog filter description
     */
    IMAGE_FILES("image_files"),

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
    SOLUTION_FOUND("solution_found");

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
