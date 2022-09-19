package com.guflimc.brick.commands.api.context;

import com.guflimc.brick.commands.api.argument.CommandArgument;

import java.util.List;

public abstract class CommandContext<S> {

    private final S sender;
    private final List<ArgumentEntry<S, ?>> arguments;

    protected CommandContext(S sender, List<ArgumentEntry<S, ?>> arguments) {
        this.sender = sender;
        this.arguments = List.copyOf(arguments);
    }

    public final S sender() {
        return sender;
    }

    public final <T> T argument(String name) {
        return arguments.stream()
                .filter(entry -> entry.argument.name().equalsIgnoreCase(name))
                .findFirst().map(entry -> (T) entry.value).orElse(null);
    }

    public final <T> T argument(int index) {
        return (T) arguments.get(index).value;
    }

    public record ArgumentEntry<S, T>(CommandArgument<S, T> argument, T value) {
    }

}
