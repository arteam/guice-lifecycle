package com.github.arteam.guice.lifecycle;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Date: 7/26/14
 * Time: 11:15 PM
 * Lifecycle manager
 *
 * @author Artem Prigoda
 */
@Singleton
public class Lifecycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lifecycle.class);

    @Inject
    private LifecycleRegistry registry;

    @Inject
    private LifecycleConfig config;

    private boolean tasksAreStarted;
    private boolean tasksAreShutdown;

    @NotNull
    private ReentrantLock lock = new ReentrantLock();

    @Inject
    private void init() {
        config.configure();
    }

    /**
     * Perform all start tasks and add a runtime shutdown hook
     */
    public void manage() {
        lock.lock();
        try {
            performStartTasks();
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    performShutdownTasks();
                }
            }));
        } finally {
            lock.unlock();
        }
    }

    /**
     * Perform start tasks
     */
    public void performStartTasks() {
        lock.lock();
        try {
            if (tasksAreStarted) {
                throw new IllegalStateException("Start tasks have already been performed");
            }
            for (StartupTask task : registry.getStartupTasks()) {
                try {
                    task.start();
                } catch (Exception e) {
                    LOGGER.error("Unable start task: " + task.getName(), e);
                }
            }
            tasksAreStarted = true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Perform shutdown tasks immediately
     */
    public void performShutdownTasks() {
        lock.lock();
        try {
            if (!tasksAreStarted) {
                throw new IllegalStateException("Start tasks haven't been performed yet");
            }
            if (tasksAreShutdown) {
                throw new IllegalStateException("Shutdown tasks have already been performed");
            }
            for (ShutdownTask task : registry.getShutdownTasks()) {
                try {
                    task.shutdown();
                } catch (Exception e) {
                    LOGGER.error("Unable shutdown task: " + task.getName(), e);
                }
            }
            tasksAreShutdown = true;
        } finally {
            lock.unlock();
        }
    }
}
