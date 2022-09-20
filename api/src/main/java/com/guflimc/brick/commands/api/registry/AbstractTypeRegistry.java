package com.guflimc.brick.commands.api.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTypeRegistry<V> {

    private final Map<Class<?>, V> registry = new ConcurrentHashMap<>();

    protected <T> void register(Class<T> type, V value) {
        registry.put(type, value);
    }

    protected final <T> V find(Class<T> type) {
        for (Class<?> cls : registry.keySet()) {
            if (cls.equals(type)) {
                return registry.get(type);
            }
        }

        for (Class<?> cls : registry.keySet()) {
            if (cls.isAssignableFrom(type)) {
                return registry.get(type);
            }
        }

        throw new IllegalArgumentException("The given type is not registered with this registry.");
    }

}
