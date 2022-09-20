package com.guflimc.brick.commands.api.registry;

import com.guflimc.brick.commands.api.argument.CommandArgument;

public class ArgumentTypeRegistry<S> extends AbstractTypeRegistry<ArgumentTypeRegistry.ArgumentFactory<S, ?>> {

    @FunctionalInterface
    public interface ArgumentFactory<S, T> {
        CommandArgument<S, T> create(String name);
    }

    //

    public <T> void register(Class<T> type, ArgumentFactory<S, T> value) {
        super.register(type, value);
    }

    public <T> CommandArgument<S, T> create(Class<T> type, String name) {
        return (CommandArgument<S, T>) find(type).create(name);
    }

}
