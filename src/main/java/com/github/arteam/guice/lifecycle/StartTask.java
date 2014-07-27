package com.github.arteam.guice.lifecycle;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 7/26/14
 * Time: 11:10 PM
 * Task for perform at startup
 *
 * @author Artem Prigoda
 */
public abstract class StartTask extends NamedTask {

    protected StartTask() {
    }

    protected StartTask(@NotNull String name) {
        super(name);
    }

    public abstract void start() throws Exception;
}
