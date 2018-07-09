package edu.kit.ipd.pp.viper.model.interpreter;

/**
 * Enumeration of all supported step results.
 */
public enum StepResult {
    /** Step result when there are no more solutions left*/
    NO_MORE_SOLUTIONS,
    /** Step result when a solution was found*/
    SOLUTION_FOUND,
    /** Step result when there are steps remaining */
    STEPS_REMAINING
}
