package com.guflimc.brick.commands.api.argument.types;

import com.guflimc.brick.commands.api.argument.CommandArgumentSuggestion;
import com.guflimc.brick.commands.api.context.CommandArgumentContext;

import java.util.Collections;
import java.util.List;

public class GenericArgument<S, T> extends AbstractArgument<S, T> {

    private final ArgumentParser<S, T> parser;
    private ArgumentCondition<S, T> condition;
    private ArgumentSuggestions<S, T> suggestions;

    public GenericArgument(Class<T> type, String name, ArgumentParser<S, T> parser) {
        super(type, name);
        this.parser = parser;
    }

    public void withCondition(ArgumentCondition<S, T> condition) {
        this.condition = condition;
    }

    public void withSuggestions(ArgumentSuggestions<S, T> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public T parse(CommandArgumentContext<S> context, String input) {
        return parser.parse(context, input);
    }

    @Override
    public boolean validate(CommandArgumentContext<S> context, T value) {
        return condition == null || condition.test(context, value);
    }

    @Override
    public List<CommandArgumentSuggestion> suggestions(CommandArgumentContext<S> context, String input) {
        return suggestions == null ? Collections.emptyList() : suggestions.accept(context, input);
    }

    //

    @FunctionalInterface
    public interface ArgumentSuggestions<S, T> {
        List<CommandArgumentSuggestion> accept(CommandArgumentContext<S> context, String input);
    }

    @FunctionalInterface
    public interface ArgumentParser<S, T> {
        T parse(CommandArgumentContext<S> context, String input);
    }

    @FunctionalInterface
    public interface ArgumentCondition<S, T> {
        boolean test(CommandArgumentContext<S> context, T value);
    }

}
