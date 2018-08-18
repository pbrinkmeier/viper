package edu.kit.ipd.pp.viper.view;

public enum GUIComponentID {
    /**
     * Identifier of the main window
     */
    FRAME_MAIN("frame_main"),
    
    /**
     * Identifier of the about window
     */
    FRAME_ABOUT("frame_about"),
    
    /**
     * Identifier of the show standard lib window
     */
    FRAME_SHOW_STD("frame_show_std"),
    
    /**
     * Identifier of the help window
     */
    FRAME_WELCOME("frame_welcome"),
    
    /**
     * Identifier of the "new" button
     */
    BUTTON_NEW("button_new"),
    
    /**
     * Identifier of the "open" button
     */
    BUTTON_OPEN("button_open"),
    
    /**
     * Identifier of the "save" button
     */
    BUTTON_SAVE("button_save"),
    
    /**
     * Identifier of the "parse" button
     */
    BUTTON_PARSE("button_parse"),
    
    /**
     * Identifier of the "format" button
     */
    BUTTON_FORMAT("button_format"),
    
    /**
     * Identifier of the "stepback" button
     */
    BUTTON_STEPBACK("button_stepback"),
    
    /**
     * Identifier of the "step" button
     */
    BUTTON_STEP("button_step"),
    
    /**
     * Identifier of the "next solution" button
     */
    BUTTON_NEXT_SOLUTION("button_next_solution"),
    
    /**
     * Identifier of the "cancel" button
     */
    BUTTON_CANCEL("button_cancel"),
    
    /**
     * Identifier of the "send" button
     */
    BUTTON_SEND("button_send"),
    
    /**
     * Identifier of the "zoom in" button
     */
    BUTTON_ZOOM_IN("button_zoom_in"),
    
    /**
     * Identifier of the "zoom out" button
     */
    BUTTON_ZOOM_OUT("button_zoom_out"),
    
    /**
     * Identifier of the "save unsaved changes" dialog caused by the "exit" command
     */
    DIALOG_UNSAVED_ON_EXIT("dialog_unsaved_on_exit"),
    
    /**
     * Identifier of the "save unsaved changes" dialog caused by the "new" command
     */
    DIALOG_UNSAVED_ON_NEW("dialog_unsaved_on_new"),
    
    /**
     * Identifier of the "save unsaved changes" dialog caused by the "open" command
     */
    DIALOG_UNSAVED_ON_OPEN("dialog_unsaved_on_open");

    private final String name;
    private GUIComponentID(String name) {
        this.name = name;
    }
    
    /**
     * Getter-Method for the String ID of the GUI component
     * 
     * @return the ID of the component as a String
     */
    public String toString() {
        return this.name;
    }
}
