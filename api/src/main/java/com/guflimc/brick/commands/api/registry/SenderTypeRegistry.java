package com.guflimc.brick.commands.api.registry;

import java.util.function.Function;

public class SenderTypeRegistry<S> extends AbstractTypeRegistry<Function<S, ?>> {

    public <T> void register(Class<T> type, Function<S, T> value) {
        super.register(type, value);
    }

    public <T> T adapt(Class<T> type, S sender) {
        return (T) find(type).apply(sender);
    }

}
