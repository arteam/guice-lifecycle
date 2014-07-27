package com.github.arteam.guice.lifecycle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 7/26/14
 * Time: 11:52 PM
 *
 * @author Artem Prigoda
 */
@Singleton
public class MQClient {

    @Inject
    private EventBus eventBus;

    @Inject
    private MessageHandler messageHandler;

    public List<String> getBacklog() {
        return messageHandler.backlog;
    }

    public void registerHandler() {
        System.out.println("Accepting messages...");
        eventBus.register(messageHandler);
    }

    public void closeConnection() {
        eventBus.unregister(messageHandler);
        System.out.println("MQ client is down");
    }

    private static class MessageHandler {

        List<String> backlog = new ArrayList<String>();

        @Subscribe
        public void handle(String message) {
            backlog.add(message);
        }
    }
}
