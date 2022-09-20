package com.guflimc.brick.commands.api.builder;

import com.guflimc.brick.commands.api.CommandDispatcher;

public class CommandBuilder<S> extends AbstractCommandBuilder<S, CommandBuilder<S>> {

    public static <S> CommandBuilder<S> of(String literals) {
        return new CommandBuilder<S>().withLiterals(literals);
    }

    public static <S> ExtendedCommandBuilder<S> of(CommandDispatcher<S> dispatcher, String literals) {
        return new ExtendedCommandBuilder<>(dispatcher).withLiterals(literals);
    }

    //

    protected CommandBuilder() {}

}
