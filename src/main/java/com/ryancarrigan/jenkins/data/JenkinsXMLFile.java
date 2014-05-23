package com.ryancarrigan.jenkins.data;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Suave Peanut on 5/22/14.
 */
public abstract class JenkinsXMLFile {
    protected Element root;
    protected Logger log = LoggerFactory.getLogger(getClass());

    public JenkinsXMLFile() {

    }

    public JenkinsXMLFile(final Document document, final String expectedRootName) {
        root = document.getRootElement();

        final String actualRootName = root.getName();
        if (!actualRootName.equals(expectedRootName))
            throw new NullPointerException(String.format("Invalid input file. Expected: <%s> Actual: <%s>",
                    expectedRootName, actualRootName));
    }

    protected Boolean isValidFile(final String desiredRootName) {
        return root.getName().equals(desiredRootName);
    }

    private Document getDocument(final String xmlFileName) {
        try {
            return new SAXBuilder().build(xmlFileName);
        } catch (JDOMException e) {
            log.error("JDOMException", e);
        } catch (IOException e) {
            log.error("IOException", e);
        }
        throw new NullPointerException("Error getting document from file name");
    }

}
