package com.guflimc.brick.commands.api.builder;

import com.guflimc.brick.commands.api.CommandDispatcher;

public class ExtendedCommandBuilder<S> extends AbstractCommandBuilder<S, ExtendedCommandBuilder<S>> {

    private final CommandDispatcher<S> dispatcher;

    ExtendedCommandBuilder(CommandDispatcher<S> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public <T> ExtendedCommandBuilder<S> withArgument(String name, Class<T> type) {
        return withArgument(dispatcher.argumentTypes().create(type, name));
    }

}