package edu.kit.ipd.pp.viper.controller;

/**
 * Command for exiting the program.
 */
public class CommandExit extends Command {
    /**
     * Initializes a new exit command.
     */
    public CommandExit() {
    }

    /**
     * Executes the command.
     */
    public void execute() {
        System.exit(0);
    }
}
