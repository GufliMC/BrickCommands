package com.guflimc.brick.commands.api.context;

import java.util.List;

public class CommandArgumentContext<S> extends CommandContext<S> {

    private final int index;

    public CommandArgumentContext(S sender, List<ArgumentEntry<S, ?>> arguments, int index) {
        super(sender, arguments);
        this.index = index;
    }

    public final int index() {
        return index;
    }

}
