package com.guflimc.brick.commands.api.argument.types;

import com.guflimc.brick.commands.api.context.CommandArgumentContext;

import java.lang.reflect.InvocationTargetException;

public class NumberArgument<S, T extends Number & Comparable<T>> extends AbstractArgument<S, T> {

    private T min;
    private T max;

    public NumberArgument(Class<T> type, String name) {
        super(type, name);
    }

    public NumberArgument<S, T> withRange(T min, T max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public T parse(CommandArgumentContext<S> context, String input) {
        try {
            return (T) type().getMethod("valueOf", String.class).invoke(null, input);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validate(CommandArgumentContext<S> context, T value) {
        boolean lb = min == null || min.compareTo(value) < 0;
        boolean ub = max == null || max.compareTo(value) >= 0;
        return lb && ub;
    }
}
