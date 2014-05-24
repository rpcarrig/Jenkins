package com.ryancarrigan.jenkins.data;

import com.ryancarrigan.jenkins.download.FileDownloader;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Created by Suave Peanut on 5/22/14.
 */
public class JenkinsXMLFile extends FileDownloader {

    private String localFile;
    protected Element root;

    public JenkinsXMLFile(final Element element, final String expectedRootName) {
        root = element;

        final String actualRootName = root.getName();
        if (!actualRootName.equals(expectedRootName))
            log.error(String.format("Invalid input file. Expected: <%s> Actual: <%s>",
                    expectedRootName, actualRootName));
    }

    public JenkinsXMLFile(final Document document, final String expectedRootName) {
        this(document.getRootElement(), expectedRootName);
    }

    protected Boolean isValidFile(final String desiredRootName) {
        return root.getName().equals(desiredRootName);
    }

}
