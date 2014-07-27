package com.github.arteam.guice.lifecycle;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

/**
 * Date: 7/26/14
 * Time: 11:30 PM
 * <p/>
 * Base class for the lifecycle configuration.
 * <ol>
 * <li>Override <b>configure</b> method add needed start and shutdown tasks.</li>
 * <li>Bind your implementation to this class in a Guice module:
 * <pre>
 *     protected void configure() {
 *         bind(LifecycleConfig.class).to(YourLifecycleConfig.class);
 *     }
 *     </pre>
 * </li>
 * </ol>
 *
 * @author Artem Prigoda
 */
public abstract class LifecycleConfig {

    @Inject
    private LifecycleRegistry registry;

    public abstract void configure();

    protected boolean addStartupTask(@NotNull StartupTask startupTask) {
        return registry.getStartupTasks().add(startupTask);
    }

    protected boolean addShutdownTask(@NotNull ShutdownTask shutdownTask) {
        return registry.getShutdownTasks().add(shutdownTask);
    }
}
