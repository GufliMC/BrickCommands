package com.guflimc.brick.commands.api;

public interface Command {

    String[] literals();

    // TODO conditions

    void call(CommandExecutionContext context);

}
