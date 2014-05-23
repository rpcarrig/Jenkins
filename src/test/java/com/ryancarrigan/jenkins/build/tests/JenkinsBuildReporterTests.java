package com.ryancarrigan.jenkins.build.tests;

import com.ryancarrigan.jenkins.FileDownloader;
import com.ryancarrigan.jenkins.data.file.Job;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * PACKAGE_NAME
 *
 * @author Ryan P. Carrigan
 * @since 5/22/14.
 */
public class JenkinsBuildReporterTests {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Boolean inProgress = true;

    @BeforeMethod
    public void setup() {
        log.info("Setup");
    }

    @Test
    public void test1() {
        log.info("Starting test1");
        final String source = "http://sectstjnk01.us.gspt.net:8080/parsing/RegressionRevival_PetSmart_TST01/38/api/xml?depth=2";
        final FileDownloader downloader = new FileDownloader(source);

        new Thread(updateProgress(downloader)).start();
        new Thread(startDownload(downloader)).run();
    }

    private Runnable startDownload(final FileDownloader downloader) {
        return new Runnable() {
            @Override
            public void run() {
                log.info("Start download");
                downloader.downloadFile("output.xml");
                log.info("Finished download");
                inProgress = false;
            }
        };
    }

    private Runnable updateProgress(final FileDownloader downloader) {
        return new Runnable() {
            @Override
            public void run() {
                log.info("Start progress watch");
                Integer progress = -1;
                do {
                    if (progress > 0)
                        log.info(System.currentTimeMillis() + ": " + progress);

                } while ((progress = downloader.getBytesRead()) > 0);
            }
        };
    }

}
