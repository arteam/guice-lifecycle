package com.github.arteam.guice.lifecycle;

import com.google.inject.Singleton;

/**
 * Date: 7/26/14
 * Time: 11:42 PM
 *
 * @author Artem Prigoda
 */
@Singleton
public class HttpClient {

    private boolean started;

    public void start() {
        started = true;
        System.out.println("HTTP client is started");
    }

    public String makeRequest() {
        return started ? "OK" : "Error";
    }

    public void shutdown() {
        System.out.println("HTTP client is down");
        started = false;
    }
}
