package com.github.arteam.guice.lifecycle;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 7/26/14
 * Time: 11:43 PM
 *
 * @author Artem Prigoda
 */
public class LifecycleTest {

    Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(LifecycleConfig.class).to(TestLifecycleConfig.class);
            bind(EventBus.class).toInstance(new EventBus());
        }
    });

    @Test
    public void testManage() {
        injector.getInstance(Lifecycle.class).manage();

        assertThat(injector.getInstance(HttpClient.class).makeRequest()).isEqualTo("OK");
        injector.getInstance(EventBus.class).post("New notification");
        assertThat(injector.getInstance(MQClient.class).getBacklog()).contains("New notification");
    }

    @Test
    public void testManualStartupAndShutdown() {
        Lifecycle lifecycle = injector.getInstance(Lifecycle.class);
        lifecycle.performStartTasks();

        HttpClient httpClient = injector.getInstance(HttpClient.class);
        EventBus eventBus = injector.getInstance(EventBus.class);
        MQClient mqClient = injector.getInstance(MQClient.class);
        assertThat(httpClient.makeRequest()).isEqualTo("OK");
        eventBus.post("New notification");
        assertThat(mqClient.getBacklog()).contains("New notification");

        lifecycle.performShutdownTasks();

        assertThat(httpClient.makeRequest()).isEqualTo("Error");
        eventBus.post("Another notification");
        assertThat(mqClient.getBacklog()).doesNotContain("Another notification");
    }

    @Test
    public void testNoDoubleStartup() throws Throwable {
        final Lifecycle lifecycle = injector.getInstance(Lifecycle.class);
        final BlockingQueue<Throwable> throwables = new LinkedBlockingQueue<Throwable>();
        ExecutorService executor = Executors.newFixedThreadPool(2, new ThreadFactory() {

            ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = defaultThreadFactory.newThread(r);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        throwables.add(e);
                    }
                });
                return thread;
            }
        });
        for (int i = 0; i < 2; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    lifecycle.manage();
                }
            });
        }
        executor.shutdown();

        Throwable throwable = throwables.poll(10, TimeUnit.SECONDS);
        if (throwable != null) {
            assertThat(throwable).isExactlyInstanceOf(IllegalStateException.class);
        } else {
            Assert.fail("Should have been exception on second initialization");
        }
    }
}
