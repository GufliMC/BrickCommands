package com.guflimc.brick.commands.api.argument.types;

import com.guflimc.brick.commands.api.context.CommandArgumentContext;

public class BooleanArgument<S> extends AbstractArgument<S, Boolean> {

    public BooleanArgument(String name) {
        super(Boolean.class, name);
    }

    @Override
    public Boolean parse(CommandArgumentContext<S> context, String input) {
        return Boolean.valueOf(input);
    }

}
