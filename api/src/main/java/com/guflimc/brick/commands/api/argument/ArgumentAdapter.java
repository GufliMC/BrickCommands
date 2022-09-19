package com.guflimc.brick.commands.api.argument;

import java.util.function.Function;

public class ArgumentAdapter<F, T> {

    private final Class<F> fromType;
    private final Class<T> toType;

    private final Function<F, T> adapter;

    public ArgumentAdapter(Class<F> fromType, Class<T> toType, Function<F, T> adapter) {
        this.fromType = fromType;
        this.toType = toType;
        this.adapter = adapter;
    }

    public Class<F> fromType() {
        return fromType;
    }

    public Class<T> toType() {
        return toType;
    }

    public T adapt(F from) {
        return adapter.apply(from);
    }
}
