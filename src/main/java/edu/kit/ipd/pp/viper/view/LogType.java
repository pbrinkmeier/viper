package edu.kit.ipd.pp.viper.view;

/**
 * A logtype controls the output of the console output field. This mainly
 * includes color, however certain LogTypes may also affect whether
 */
public enum LogType {
    /**
     * Plain text information
     */
    INFO,

    /**
     * Debug messages. These messages only appear if the program was started in
     * debug mode
     */
    DEBUG,

    /**
     * Result of a unification
     */
    SUCCESS,

    /**
     * Error messages of the program
     */
    ERROR
}
