package com.ryancarrigan.jenkins.build.tests;

import com.ryancarrigan.jenkins.FileDownloader;
import com.ryancarrigan.jenkins.data.file.Build;
import org.jdom2.Document;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

/**
 * com.ryancarrigan.jenkins.build.tests
 *
 * @author Ryan P. Carrigan
 * @since 5/23/14.
 */
public class DataTests extends TestBase {
    final FileDownloader.API api = FileDownloader.API.XML;
    final FileDownloader.Depth depth = FileDownloader.Depth.TWO;
    private Document xmlFile;

    @BeforeMethod
    public void downloadFile() {
        log.info("Starting test...");
    }

    @Test
    public void testFileDownloader() {
        final String url = "http://sectstjnk01.us.gspt.net:8080/view/Regression%20Revival/job/RegressionRevival_CBK_TST01/517";
        FileDownloader d = new FileDownloader(url, api, depth);
        xmlFile = d.getDocument("output.xml");
    }

}
