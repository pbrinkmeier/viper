package edu.kit.ipd.pp.viper.controller;

/**
 * Abstract command class. Commands encapsulate the behavior initiated by
 * GUI-interactions like pressing a button.
 */
public abstract class Command {
    /**
     * Initializes a new command. Subclasses will mostly use their constructor to
     * initialize references to other required objects.
     */
    protected Command() {
    }

    /**
     * Executes the command.
     */
    public abstract void execute();
}
