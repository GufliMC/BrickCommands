package com.guflimc.brick.commands.api.argument;

import com.guflimc.brick.commands.api.context.CommandArgumentContext;

import java.util.List;

public interface CommandArgument<S, T> {

    Class<T> type();

    String name();

    T parse(CommandArgumentContext<S> context, String input);

    boolean validate(CommandArgumentContext<S> context, T value);

    List<CommandArgumentSuggestion> suggestions(CommandArgumentContext<S> context, String input);

}
