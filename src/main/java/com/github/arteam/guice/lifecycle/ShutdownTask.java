package com.github.arteam.guice.lifecycle;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 7/26/14
 * Time: 11:10 PM
 * Task to perform at shutdown
 *
 * @author Artem Prigoda
 */
public abstract class ShutdownTask extends NamedTask {

    protected ShutdownTask() {
    }

    protected ShutdownTask(@NotNull String name) {
        super(name);
    }

    public abstract void shutdown() throws Exception;
}
