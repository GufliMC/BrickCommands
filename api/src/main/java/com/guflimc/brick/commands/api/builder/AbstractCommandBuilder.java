package com.guflimc.brick.commands.api.builder;

import com.guflimc.brick.commands.api.Command;
import com.guflimc.brick.commands.api.argument.CommandArgument;
import com.guflimc.brick.commands.api.argument.types.GenericArgument;
import com.guflimc.brick.commands.api.context.CommandExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class AbstractCommandBuilder<S, R extends AbstractCommandBuilder<S, R>> {

    private final static String SPACE_QUOTED = Pattern.quote(" ");

    //

    private String[] literals;
    private final List<CommandArgument<S, ?>> arguments = new ArrayList<>();
    private Consumer<CommandExecutionContext<S>> executor;

    protected AbstractCommandBuilder() {
    }

    //

    private R thiz() {
        return (R) this;
    }

    //

    public R withLiterals(String literals) {
        this.literals = literals.split(SPACE_QUOTED);
        return thiz();
    }

    public R withArgument(CommandArgument<S, ?> argument) {
        arguments.add(argument);
        return thiz();
    }

    public <T> R withArgument(String name, Class<T> type,
                              GenericArgument.ArgumentParser<S, T> parser) {
        return withArgument(new GenericArgument<S, T>(type, name, parser));
    }

    public R withExecutor(Consumer<CommandExecutionContext<S>> executor) {
        this.executor = executor;
        return thiz();
    }

    public final Command<com.guflimc.brick.commands.api.tests.mock.MockSender> build() {
        return new Command<>(literals, arguments.toArray(CommandArgument[]::new), executor);
    }

}
