package com.github.arteam.guice.lifecycle;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 7/26/14
 * Time: 11:09 PM
 * Registry of lifecycle tasks
 *
 * @author Artem Prigoda
 */
@Singleton
public class LifecycleRegistry {

    @NotNull
    private List<StartupTask> startupTasks = Collections.synchronizedList(new ArrayList<StartupTask>());

    @NotNull
    private List<ShutdownTask> shutdownTasks = Collections.synchronizedList(new ArrayList<ShutdownTask>());

    @NotNull
    public List<StartupTask> getStartupTasks() {
        return startupTasks;
    }

    @NotNull
    public List<ShutdownTask> getShutdownTasks() {
        return shutdownTasks;
    }
}
