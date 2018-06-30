package edu.kit.ipd.pp.viper.controller;

public class CommandExit extends Command {
    /**
     * @param running 
     * @param interpreterManager
     */
    public CommandExit() { }

    /**
     * @return
     */
    public void execute() {
    	System.exit(0);
    }
}
