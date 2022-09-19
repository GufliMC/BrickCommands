package com.guflimc.brick.commands.api.argument.types;

import com.guflimc.brick.commands.api.context.CommandArgumentContext;

public class StringArgument<S> extends AbstractArgument<S, String> {

    private String regex = null;

    public StringArgument(String name) {
        super(String.class, name);
    }

    public StringArgument<S> withRegex(String regex) {
        this.regex = regex;
        return this;
    }

    @Override
    public String parse(CommandArgumentContext<S> context, String input) {
        return input;
    }

    @Override
    public boolean validate(CommandArgumentContext<S> context, String value) {
        return regex == null || value.matches(regex);
    }

}
