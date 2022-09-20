package com.guflimc.brick.commands.api.tree;

import com.guflimc.brick.commands.api.Command;
import com.guflimc.brick.commands.api.argument.CommandArgumentSuggestion;
import com.guflimc.brick.commands.api.builder.CommandBuilder;
import com.guflimc.brick.commands.api.tree.node.Node;
import com.guflimc.brick.commands.api.tree.node.NodeCommand;
import com.guflimc.brick.commands.api.tree.node.NodeLiteral;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandTree<S> {

    private final NodeLiteral root = new NodeLiteral("");

    private record SearchResult<S>(NodeCommand<S> node, int depth) {
    }

    public record ParseResult<S>(NodeCommand<S> node, String[] stringArgs, boolean exact) {
    }

    //

    private final static Pattern ARGUMENT_REGEX = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");

    public void register(Command<S> command) {
        registerRecursive(command, 0, root);
    }

    private void registerRecursive(Command<S> command, int index, NodeLiteral node) {
        if (command.literals().length - 1 == index) {
            node.children().add(new NodeCommand<>(command.literals()[index], command));
            return;
        }

        for (Node child : node.children()) {
            if (child instanceof NodeLiteral nl && child.label().equalsIgnoreCase(command.literals()[index])) {
                registerRecursive(command, index + 1, nl);
                return;
            }
        }

        NodeLiteral newNode = new NodeLiteral(command.literals()[index]);
        node.children().add(newNode);
        registerRecursive(command, index + 1, newNode);
    }

    /**
     * Parses the given input and returns the command that matches the input.
     * If a command is found but the amount of arguments is wrong, exact will be false.
     *
     * @param input The input to parse
     * @return The result of the parse
     */
    public Optional<ParseResult<S>> parse(String input) {
        String[] in = split(input);

        List<SearchResult<S>> search = new ArrayList<>();
        for (Node child : root.children()) {
            parseRecursive(in, 0, search, child);
        }

        if (search.isEmpty()) {
            // TODO COMMAND DOES NOT EXIST
            return Optional.empty();
        }

        // We loop through all found, starting with the one with the most literals
        search.sort(Comparator.comparingInt((SearchResult<S> sr) -> sr.depth).reversed());

        for (SearchResult<S> sr : search) {
            String[] args = Arrays.copyOfRange(in, sr.depth + 1, in.length);
            int diff = args.length - sr.node.command().arguments().length;
            if (diff == 0) {
                // Exact amount of arguments
                return Optional.of(new ParseResult<>(sr.node, args, true));
            }
            if (diff < 0) {
                // best match but it doesn't have enough arguments
                return Optional.of(new ParseResult<>(sr.node, args, false));
            }
        }

        // best match but too many arguments
        NodeCommand<S> node = search.get(0).node;
        String[] args = Arrays.copyOfRange(in, node.command().literals().length, in.length);
        return Optional.of(new ParseResult<>(node, args, false));
    }

    private void parseRecursive(String[] input, int index, List<SearchResult<S>> result, Node current) {
        if (index >= input.length || !current.label().equalsIgnoreCase(input[index])) {
            return;
        }

        if (current instanceof NodeCommand<?> node) {
            result.add(new SearchResult<>((NodeCommand<S>) node, index));
            return;
        }

        NodeLiteral node = (NodeLiteral) current;
        for (Node child : node.children()) {
            parseRecursive(input, index + 1, result, child);
        }
    }

    private String[] split(String input) {
        List<String> matchList = new ArrayList<>();
        Matcher matcher = ARGUMENT_REGEX.matcher(input);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Add double-quoted string without the quotes
                matchList.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                // Add single-quoted string without the quotes
                matchList.add(matcher.group(2));
            } else {
                // Add unquoted word
                matchList.add(matcher.group());
            }
        }

        return matchList.toArray(String[]::new);
    }

    public List<CommandArgumentSuggestion> suggestions(String input) {
        return Collections.emptyList();
    }

}
