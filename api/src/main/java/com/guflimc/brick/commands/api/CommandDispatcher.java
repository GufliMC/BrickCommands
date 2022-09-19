package com.guflimc.brick.commands.api;

import com.guflimc.brick.commands.api.argument.ArgumentAdapter;
import com.guflimc.brick.commands.api.argument.CommandArgument;
import com.guflimc.brick.commands.api.context.CommandArgumentContext;
import com.guflimc.brick.commands.api.context.CommandContext;
import com.guflimc.brick.commands.api.context.CommandExecutionContext;
import com.guflimc.brick.commands.api.tree.CommandTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class CommandDispatcher<S> {

    private final Map<Class<?>, ArgumentAdapter<S, ?>> senderAdapters = new ConcurrentHashMap<>();

    private final Class<S> senderType;
    private final CommandTree<S> tree = new CommandTree<>();

    public CommandDispatcher(Class<S> senderType) {
        this.senderType = senderType;
    }

    public <U> void withSenderAdapter(ArgumentAdapter<S, U> adapter) {
        senderAdapters.put(adapter.toType(), adapter);
    }

    public <U> void withSenderAdapter(Class<U> type, Function<S, U> adapter) {
        withSenderAdapter(new ArgumentAdapter<>(senderType, type, adapter));
    }

    public CommandTree<S> tree() {
        return tree;
    }

    public void dispatch(S sender, String input) {
        Optional<CommandTree.ParseResult<S>> opt = tree.parse(input);
        if (opt.isEmpty()) {
            System.out.println("A");
            // TODO unknown command message
            return;
        }

        CommandTree.ParseResult<S> result = opt.get();
        if (!result.exact()) {
            System.out.println("B");
            // TODO usage message
            return;
        }

        // TODO conditions check

        String[] stringArgs = result.stringArgs();
        List<CommandContext.ArgumentEntry<S, ?>> arguments = new ArrayList<>();
        for (int i = 0; i < stringArgs.length; i++) {
            CommandArgumentContext<S> argumentContext = new CommandArgumentContext<>(sender, arguments, i);
            CommandArgument<S, ?> argument = result.node().command().arguments()[i];
            arguments.add(adaptArgument(argumentContext, argument, stringArgs[i]));
        }

        CommandExecutionContext<S> context = new CommandExecutionContext<>(sender, arguments);
        result.node().command().executor().accept(context);
    }

    private <U> CommandContext.ArgumentEntry<S, U> adaptArgument(CommandArgumentContext<S> argumentContext,
                                                                 CommandArgument<S, U> argument, String value) {
        return new CommandContext.ArgumentEntry<>(argument, argument.type()
                .cast(argument.parse(argumentContext, value)));
    }

}
