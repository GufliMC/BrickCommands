package com.guflimc.brick.commands.api;

import com.guflimc.brick.commands.api.argument.CommandArgument;
import com.guflimc.brick.commands.api.context.CommandExecutionContext;

import java.util.function.Consumer;

public record Command<S>(String[] literals, CommandArgument<S, ?>[] arguments,
                         Consumer<CommandExecutionContext<S>> executor) {

}
