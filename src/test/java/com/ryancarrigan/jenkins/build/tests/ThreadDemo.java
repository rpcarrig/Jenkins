package com.ryancarrigan.jenkins.build.tests;

/**
 * com.ryancarrigan.jenkins.build.tests
 *
 * @author Ryan P. Carrigan
 * @since 5/22/14.
 */
public class ThreadDemo {

    private Thread r1() {
        return new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }, "Thread R1");
    }

    private Runnable r2() {
        return new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }, "Thread R2");
    }
}
