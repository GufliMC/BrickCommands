package com.guflimc.brick.commands.api.tree.node;

public abstract class Node {

    private final String label;

    Node(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}