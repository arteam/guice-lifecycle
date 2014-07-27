package com.github.arteam.guice.lifecycle;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 7/27/14
 * Time: 12:50 PM
 * A task with a name
 *
 * @author Artem Prigoda
 */
public abstract class NamedTask {

    private static final String UNDEFINED = "undefined";

    @NotNull
    private String name;

    protected NamedTask() {
        name = UNDEFINED;
    }

    protected NamedTask(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
