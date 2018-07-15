package edu.kit.ipd.pp.viper.view;

/**
 * Interface for all GUI elements containing clickables (buttons, menu items
 * etc.) that can change state (enabled/disabled).
 */
public interface HasClickable {
    /**
     * Switch function. Toggles between different possible clickable states and
     * enables/disables specific clickables.
     * 
     * @param state The new state to change to
     */
    void switchClickableState(ClickableState state);
}
