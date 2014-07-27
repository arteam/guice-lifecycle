package com.github.arteam.guice.lifecycle.bogus;

import com.github.arteam.guice.lifecycle.LifecycleConfig;
import com.github.arteam.guice.lifecycle.ShutdownTask;
import com.github.arteam.guice.lifecycle.StartupTask;
import com.google.inject.Inject;

/**
 * Date: 7/27/14
 * Time: 7:43 PM
 *
 * @author Artem Prigoda
 */
public class BogusLifecycleConfig extends LifecycleConfig {

    @Inject
    private BogusTask bogusTask;

    @Override
    public void configure() {
        addStartupTask(new StartupTask("start-bogus") {
            @Override
            public void start() throws Exception {
                bogusTask.start();
            }
        });
        addShutdownTask(new ShutdownTask("stop-bogus") {
            @Override
            public void shutdown() throws Exception {
                bogusTask.shutdown();
            }
        });
    }
}
