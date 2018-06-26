package edu.kit.ipd.pp.viper.controller;

public abstract class Command {
    protected Command() {
    }

    public abstract void execute();
}