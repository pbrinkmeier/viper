package edu.kit.ipd.pp.viper.controller;

public enum LanguageKey {
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
