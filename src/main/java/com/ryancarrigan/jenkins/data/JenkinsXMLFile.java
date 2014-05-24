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

    public JenkinsXMLFile(final Element element, final String... expectedNames) {
//        log.info(String.format("Creating new <%s> XML file. Setting root node to <%s>", getClass().getSimpleName(),
//                element.getName()));
        this.root = element;
        assert(isValidRootName(expectedNames));
    }

    private Boolean isValidRootName(final String... expectedNames) {
        final String actualRootName = root.getName();
        final StringBuilder nameOutput = new StringBuilder();
        for (final String name : expectedNames) {
            if (actualRootName.equals(name))
                return true;
            else nameOutput.append(String.format("(%s)", name));
        }
        log.error(String.format("Invalid input file. Expected: <%s> Actual: <%s>",
                nameOutput.toString(), root.getName()));
        return false;
    }

    public JenkinsXMLFile(final Document document, final String expectedRootName) {
        this(document.getRootElement(), expectedRootName);
    }

    protected Boolean isValidFile(final String desiredRootName) {
        return root.getName().equals(desiredRootName);
    }

}
