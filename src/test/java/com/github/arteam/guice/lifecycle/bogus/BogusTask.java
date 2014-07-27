package com.github.arteam.guice.lifecycle.bogus;

/**
 * Date: 7/27/14
 * Time: 7:41 PM
 *
 * @author Artem Prigoda
 */
public class BogusTask {

    public void start() {
        throw new IllegalStateException("Unable start bogus task");
    }

    public void shutdown() {
        throw new IllegalStateException("Unable shutdown bogus task");
    }
}
