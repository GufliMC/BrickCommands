package com.guflimc.brick.commands.api.context;

import com.guflimc.brick.commands.api.CommandDispatcher;

import java.util.List;

public class CommandExecutionContext<S> extends CommandContext<S> {

    public CommandExecutionContext(CommandDispatcher<S> dispatcher, S sender, List<ArgumentEntry<S, ?>> arguments) {
        super(dispatcher, sender, arguments);
    }

}
