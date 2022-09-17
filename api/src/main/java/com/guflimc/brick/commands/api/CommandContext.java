package com.guflimc.brick.commands.api;

public interface CommandContext {

    <T> T sender();

    <T> T argument(String name);

    <T> T argument(int index);

}
