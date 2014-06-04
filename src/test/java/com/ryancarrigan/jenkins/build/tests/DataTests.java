package com.ryancarrigan.jenkins.build.tests;

import com.ryancarrigan.jenkins.data.jenkins.build.Build;
import com.ryancarrigan.jenkins.download.FileDownloader;
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
        jenkinsUrl = "http://sectstjnk01.us.gspt.net:8080/";
    }

    @Test
    public void testFileDownloader() {
        Build b = new Build(
                new FileDownloader("http://sectstjnk01.us.gspt.net:8080/job/RegressionRevival_SteinMart_TST02/181/").getDocument()
        );

        log.info(b.getFullDisplayName());
    }
}
