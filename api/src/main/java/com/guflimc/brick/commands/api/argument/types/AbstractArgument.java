package com.guflimc.brick.commands.api.argument.types;

import com.guflimc.brick.commands.api.argument.CommandArgument;
import com.guflimc.brick.commands.api.argument.CommandArgumentSuggestion;
import com.guflimc.brick.commands.api.context.CommandArgumentContext;

import java.util.Collections;
import java.util.List;

public abstract class AbstractArgument<S, T> implements CommandArgument<S, T> {

    private final Class<T> type;
    private final String name;

    public AbstractArgument(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }


    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean validate(CommandArgumentContext<S> context, T value) {
        return true;
    }

    @Override
    public List<CommandArgumentSuggestion> suggestions(CommandArgumentContext<S> context, String input) {
        return Collections.emptyList();
    }

}
