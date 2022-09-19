package com.guflimc.brick.commands.api.tests;

import com.guflimc.brick.commands.api.CommandDispatcher;
import com.guflimc.brick.commands.api.argument.types.NumberArgument;
import com.guflimc.brick.commands.api.tests.mock.MockSender;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicTests {

    private static final MockSender sender = new MockSender();
    private static final CommandDispatcher<MockSender> dispatcher = new CommandDispatcher<>(MockSender.class);

    @Test
    public void testRoot() {
        AtomicBoolean executed = new AtomicBoolean(false);
        dispatcher.tree().register(b -> {
            b.withLiterals("foo").withExecutor(ctx -> {
                executed.set(true);
            });
        });

        MockSender sender = new MockSender();
        dispatcher.dispatch(sender, "foo");

        assertTrue(executed.get());
    }

    @Test
    public void testSub() {
        AtomicBoolean executed = new AtomicBoolean(false);
        dispatcher.tree().register(b -> {
            b.withLiterals("foo bar").withExecutor(ctx -> {
                executed.set(true);
            });
        });

        MockSender sender = new MockSender();
        dispatcher.dispatch(sender, "foo bar");

        assertTrue(executed.get());
    }

    @Test
    public void testRootArg() {
        AtomicReference<Integer> value = new AtomicReference<>();
        dispatcher.tree().register(b -> {
            b.withLiterals("foo").withArgument(new NumberArgument<>(Integer.class, "value")).withExecutor(ctx -> {
                value.set(ctx.argument("value"));
            });
        });

        MockSender sender = new MockSender();
        dispatcher.dispatch(sender, "foo 15");

        assertEquals(15, value.get());
    }

    @Test
    public void testSubArg() {
        AtomicReference<Integer> value = new AtomicReference<>();
        dispatcher.tree().register(b -> {
            b.withLiterals("foo bar").withArgument(new NumberArgument<>(Integer.class, "value")).withExecutor(ctx -> {
                value.set(ctx.argument("value"));
            });
        });

        MockSender sender = new MockSender();
        dispatcher.dispatch(sender, "foo bar 12");

        assertEquals(12, value.get());
    }

}
