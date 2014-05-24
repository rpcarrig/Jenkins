package com.ryancarrigan.jenkins.build.tests;

import com.ryancarrigan.jenkins.data.Jenkins;
import com.ryancarrigan.jenkins.data.file.home.HomeView;
import com.ryancarrigan.jenkins.data.file.view.View;
import com.ryancarrigan.jenkins.data.file.view.ViewJob;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * com.ryancarrigan.jenkins.build.tests
 *
 * @author Ryan P. Carrigan
 * @since 5/23/14.
 */
public class DataTests extends TestBase {
    String jenkinsUrl;

    @BeforeMethod
    public void downloadFile() {
        log.info("Starting test...");
        jenkinsUrl = "http://sectstjnk01.us.gspt.net:8080/";
    }

    @Test
    public void testFileDownloader() {
        Jenkins jenkins = new Jenkins(jenkinsUrl);

        View view = jenkins.getHome().getView("Regression Revival");
        log.info(view.getDescription());

    }
}
