package com.guflimc.brick.commands.api;

import com.guflimc.brick.commands.api.argument.CommandArgument;
import com.guflimc.brick.commands.api.argument.types.GenericArgument;
import com.guflimc.brick.commands.api.context.CommandExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class CommandBuilder<S> {

    private final static String SPACE_QUOTED = Pattern.quote(" ");

    //

    private String[] literals;
    private final List<CommandArgument<S, ?>> arguments = new ArrayList<>();
    private Consumer<CommandExecutionContext<S>> executor;

    public CommandBuilder() {
    }

    public static <S> CommandBuilder<S> of(String literals) {
        return new CommandBuilder<S>().withLiterals(literals);
    }

    public CommandBuilder<S> withLiterals(String literals) {
        this.literals = literals.split(SPACE_QUOTED);
        return this;
    }

    public CommandBuilder<S> withArgument(CommandArgument<S, ?> argument) {
        arguments.add(argument);
        return this;
    }

    public <T> CommandBuilder<S> withArgument(String name, Class<T> type,
                                              GenericArgument.ArgumentParser<S, T> parser) {
        return withArgument(new GenericArgument<S, T>(type, name, parser));
    }

    public CommandBuilder<S> withExecutor(Consumer<CommandExecutionContext<S>> executor) {
        this.executor = executor;
        return this;
    }

    public final Command<S> build() {
        return new Command<>(literals, arguments.toArray(CommandArgument[]::new), executor);
    }


}
