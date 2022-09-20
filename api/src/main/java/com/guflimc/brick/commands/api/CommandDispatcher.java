package com.guflimc.brick.commands.api;

import com.guflimc.brick.commands.api.argument.CommandArgument;
import com.guflimc.brick.commands.api.argument.CommandArgumentSuggestion;
import com.guflimc.brick.commands.api.context.CommandArgumentContext;
import com.guflimc.brick.commands.api.context.CommandContext;
import com.guflimc.brick.commands.api.context.CommandExecutionContext;
import com.guflimc.brick.commands.api.registry.ArgumentTypeRegistry;
import com.guflimc.brick.commands.api.registry.SenderTypeRegistry;
import com.guflimc.brick.commands.api.tree.CommandTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CommandDispatcher<S> {

    private final Class<S> senderType;

    private final ArgumentTypeRegistry<S> argumentTypeRegistry = new ArgumentTypeRegistry<>();
    private final SenderTypeRegistry<S> senderTypeRegistry = new SenderTypeRegistry<>();
    private final CommandTree<S> tree = new CommandTree<>();

    public CommandDispatcher(Class<S> senderType) {
        this.senderType = senderType;
    }

    public ArgumentTypeRegistry<S> argumentTypes() {
        return argumentTypeRegistry;
    }

    public SenderTypeRegistry<S> senderTypes() {
        return senderTypeRegistry;
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
            CommandArgumentContext<S> argumentContext = new CommandArgumentContext<>(this, sender, arguments, i);
            CommandArgument<S, ?> argument = result.node().command().arguments()[i];
            arguments.add(parseArgument(argumentContext, argument, stringArgs[i]));
        }

        CommandExecutionContext<S> context = new CommandExecutionContext<>(this, sender, arguments);
        result.node().command().executor().accept(context);
    }

    public List<CommandArgumentSuggestion> suggest(S sender, String input) {
        return Collections.emptyList();
    }

    private <U> CommandContext.ArgumentEntry<S, U> parseArgument(CommandArgumentContext<S> argumentContext,
                                                                 CommandArgument<S, U> argument, String value) {
        return new CommandContext.ArgumentEntry<>(argument, argument.type()
                .cast(argument.parse(argumentContext, value)));
    }

}
