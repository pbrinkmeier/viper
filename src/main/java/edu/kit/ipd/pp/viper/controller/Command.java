package edu.kit.ipd.pp.viper.controller;

/**
 * Abstract command class. Commands encapsulate the behavior initiated by
 * GUI-interactions like pressing a button.
 */
public abstract class Command {
    protected InterpreterManager interpreterManager;

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

    protected void cancelThread() {
        if (!this.interpreterManager.getThread().isPresent())
            return;

        this.interpreterManager.cancel();

        while (this.interpreterManager.getThread().get().isAlive());
    }
 }
