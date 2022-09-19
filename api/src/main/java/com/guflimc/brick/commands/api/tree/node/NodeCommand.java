package com.guflimc.brick.commands.api.tree.node;

import com.guflimc.brick.commands.api.Command;

public class NodeCommand<S> extends Node {

    private final Command<S> command;

    public NodeCommand(String label, Command<S> command) {
        super(label);
        this.command = command;
    }

    public Command<S> command() {
        return command;
    }

}
