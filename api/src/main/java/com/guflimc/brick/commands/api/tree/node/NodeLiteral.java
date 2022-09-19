package com.guflimc.brick.commands.api.tree.node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NodeLiteral extends Node {

    private final Set<Node> children = new HashSet<>();

    public NodeLiteral(String label) {
        super(label);
    }

    public Collection<Node> children() {
        return children;
    }

}