package com.ryancarrigan.jenkins.build.tests;

import com.ryancarrigan.jenkins.JenkinsFileDownloader;
import com.ryancarrigan.jenkins.data.file.View;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * com.ryancarrigan.jenkins.build.tests
 *
 * @author Ryan P. Carrigan
 * @since 5/23/14.
 */
public class DataTests extends TestBase {
    final String jenkinsUrl = "http://sectstjnk01.us.gspt.net:8080/";

    @BeforeMethod
    public void downloadFile() {
        log.info("Starting test...");
    }

    @Test
    public void testFileDownloader() {
        JenkinsFileDownloader jenkinsFileDownloader = new JenkinsFileDownloader(jenkinsUrl);

        View rr = jenkinsFileDownloader.getView("Regression Revival Framework");
        log.info(rr.getDescription());
        for (final View.Job j : rr.getJobs()) {
            log.info(j.toString());
        }

    }
}
