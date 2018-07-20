package edu.kit.ipd.pp.viper.view;

/**
 * This class holds all colors that are used in VIPER. Therefore changing the color scheme (e.g. a darker green for
 * successful unifications and solutions) can be done here, without touching multiple files.
 * 
 * Unfortunately, the visualisation using the graphviz-java library requires a Color object from the graphviz
 * package, not the common java.awt.Color object.
 */
public class ColorScheme {
    /**
     * Black color in console (used for normal text)
     */
    public static final java.awt.Color CONSOLE_BLACK = java.awt.Color.BLACK;

    /**
     * Gray color in console (used for debug mode text)
     */
    public static final java.awt.Color CONSOLE_GRAY = java.awt.Color.GRAY;

    /**
     * Green color in console (used for solutions)
     */
    public static final java.awt.Color CONSOLE_GREEN = new java.awt.Color(0, 204, 0);

    /**
     * Red color in console (used for errors)
     */
    public static final java.awt.Color CONSOLE_RED = java.awt.Color.RED;

    /**
     * White color in GUI (used for background)
     */
    public static final java.awt.Color GUI_WHITE = new java.awt.Color(250, 250, 250);

    /**
     * Red color in visualisation (used for unsuccessful unification)
     */
    public static final guru.nidi.graphviz.attribute.Color VIS_RED = guru.nidi.graphviz.attribute.Color.RED;

    /**
     * Green color in visualisation (used for successful unification)
     */
    public static final guru.nidi.graphviz.attribute.Color VIS_GREEN = guru.nidi.graphviz.attribute.Color.GREEN;
}
