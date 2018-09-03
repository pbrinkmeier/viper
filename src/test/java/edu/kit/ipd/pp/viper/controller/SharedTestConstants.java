package edu.kit.ipd.pp.viper.controller;

public class SharedTestConstants {
    /** The unformatted simpsons.pl example program */
    public static final String SIMPSONS_UNFORMATTED = "father(abe, homer). father(homer, bart).\n"
            + "father(homer, lisa). mother(marge, bart).\n\n" + "grandfather(X, Y) :-\n"
            + "father(X, Z), parent(Z, Y).\n\n" + "parent(X, Y) :- mother(X, Y).\n\n" + "parent(X, Y) :- father(X, Y).";

    /** The formatted simpsons.pl example program */
    public static final String SIMPSONS_FORMATTED = "father(abe, homer).\n" + "father(homer, bart).\n"
            + "father(homer, lisa).\n\n" + "mother(marge, bart).\n\n" + "grandfather(X, Y) :-\n" + "  father(X, Z),\n"
            + "  parent(Z, Y).\n\n" + "parent(X, Y) :-\n" + "  mother(X, Y).\n" + "parent(X, Y) :-\n"
            + "  father(X, Y).\n";

    /** An exemplary query */
    public static final String TEST_QUERY = "father(homer, bart).";
    
    /**
     * The default timeout for AssertJSwing operations.
     */
    public static final int DEFAULT_TIMEOUT = 5000;

    /**
     * The content of the input test file in the resources directory.
     */
    public static final String INPUT_TEST_FILE_CONTENT = "02340520350130523050235";
}
