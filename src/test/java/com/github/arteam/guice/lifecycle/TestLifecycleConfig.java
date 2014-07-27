package com.github.arteam.guice.lifecycle;

import com.google.inject.Inject;

/**
 * Date: 7/26/14
 * Time: 11:45 PM
 *
 * @author Artem Prigoda
 */
public class TestLifecycleConfig extends LifecycleConfig {

    @Inject
    private HttpClient httpClient;

    @Inject
    private MQClient mqClient;

    @Override
    public void configure() {
        addStartupTask(new StartupTask("start-http-client") {
            @Override
            public void start() throws Exception {
                httpClient.start();
            }
        });
        addStartupTask(new StartupTask() {
            @Override
            public void start() throws Exception {
                mqClient.registerHandler();
            }
        });
        addShutdownTask(new ShutdownTask() {
            @Override
            public void shutdown() throws Exception {
                httpClient.shutdown();
            }
        });
        addShutdownTask(new ShutdownTask("close-mq-client") {
            @Override
            public void shutdown() throws Exception {
                mqClient.closeConnection();
            }
        });
    }
}