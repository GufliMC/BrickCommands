package com.guflimc.brick.commands.api.registry;

import com.guflimc.brick.commands.api.argument.CommandArgument;
import com.guflimc.brick.commands.api.argument.types.BooleanArgument;
import com.guflimc.brick.commands.api.argument.types.NumberArgument;
import com.guflimc.brick.commands.api.argument.types.StringArgument;

public class ArgumentTypeRegistry<S> extends AbstractTypeRegistry<ArgumentTypeRegistry.ArgumentFactory<S, ?>> {

    @FunctionalInterface
    public interface ArgumentFactory<S, T> {
        CommandArgument<S, T> create(String name);
    }

    //

    public ArgumentTypeRegistry() {
        // primitive defaults
        register(Integer.class, name -> new NumberArgument<>(Integer.class, name));
        register(Double.class, name -> new NumberArgument<>(Double.class, name));
        register(Float.class, name -> new NumberArgument<>(Float.class, name));
        register(Long.class, name -> new NumberArgument<>(Long.class, name));
        register(Short.class, name -> new NumberArgument<>(Short.class, name));
        register(Byte.class, name -> new NumberArgument<>(Byte.class, name));
        register(String.class, StringArgument::new);
        register(Boolean.class, BooleanArgument::new);
    }

    //

    public <T> void register(Class<T> type, ArgumentFactory<S, T> value) {
        super.register(type, value);
    }

    public <T> CommandArgument<S, T> create(Class<T> type, String name) {
        return (CommandArgument<S, T>) find(type).create(name);
    }

}
