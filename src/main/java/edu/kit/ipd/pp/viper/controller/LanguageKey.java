package edu.kit.ipd.pp.viper.controller;

public enum LanguageKey {
    // Dialog options
    /** "Yes" dialog option */
    KEY_YES("key_yes"),

    /** "No" dialog option */
    KEY_NO("key_no"),

    /** "Cancel" dialog option */
    KEY_CANCEL("key_cancel"),

    /**
     * Message of the new file confirmation dialog asking whether unsaved changes
     * should be saved to disk
     */
    KEY_CONFIRMATION("key_confirm_new"),

    /**
     * Title of the new file confirmation dialog asking whether unsaved changes
     * should be saved to disk
     */
    KEY_CONFIRMATION_TITLE("key_confirm_new_title"),

    // File types
    /** Image files export dialog filter description */
    KEY_IMAGE_FILES("key_image_files"),

    /** TikZ files export dialog filter description */
    KEY_TIKZ_FILES("key_tikz_files"),

    /** Prolog files save dialog filter description */
    KEY_PROLOG_FILES("key_prolog_files"),

    // Console output on basic IO operations
    /** Console output message on successful opening of a file */
    KEY_OPEN_FILE_SUCCESS("key_open_file_success"),

    /** Console output message on successful saving of a file */
    KEY_SAVE_FILE_SUCCESS("key_save_file_success"),

    /** Console output message on failed opening of a file */
    KEY_OPEN_FILE_ERROR("key_open_file_error"),

    /** Console output message on failed saving of a file */
    KEY_SAVE_FILE_ERROR("key_save_file_error"),

    // Console output on export operations
    /** Console output message on successful export of a file */
    KEY_EXPORT_FILE_SUCCESS("key_export_file_success"),

    /** Console output message on failed export of a file */
    KEY_EXPORT_FILE_ERROR("key_export_file_error"),

    // Console output for query results
    /** Console output message for a possible solution */
    KEY_SOLUTION_FOUND("key_solution_found");

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
