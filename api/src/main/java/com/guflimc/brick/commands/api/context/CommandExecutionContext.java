package com.guflimc.brick.commands.api.context;

import java.util.List;

public class CommandExecutionContext<S> extends CommandContext<S> {

    public CommandExecutionContext(S sender, List<ArgumentEntry<S, ?>> arguments) {
        super(sender, arguments);
    }

}
