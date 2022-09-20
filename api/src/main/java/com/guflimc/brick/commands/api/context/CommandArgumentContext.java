package com.guflimc.brick.commands.api.context;

import com.guflimc.brick.commands.api.CommandDispatcher;

import java.util.List;

public class CommandArgumentContext<S> extends CommandContext<S> {

    private final int index;

    public CommandArgumentContext(CommandDispatcher<S> dispatcher, S sender, List<ArgumentEntry<S, ?>> arguments, int index) {
        super(dispatcher, sender, arguments);
        this.index = index;
    }

    public final int index() {
        return index;
    }

}
