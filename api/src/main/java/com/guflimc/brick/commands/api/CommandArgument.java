package com.guflimc.brick.commands.api;

import java.util.List;

public interface CommandArgument<T> {

    Class<T> type();

    T parse(CommandArgumentContext context, String input);

    List<CommandArgumentSuggestion> suggestions(CommandArgumentContext context, String input);

}
