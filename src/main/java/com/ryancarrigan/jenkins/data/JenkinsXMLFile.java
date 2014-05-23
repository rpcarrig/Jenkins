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

    public JenkinsXMLFile(final Document document) {
        root = document.getRootElement();
    }

    public JenkinsXMLFile(final String xmlFileName) {
        root = getDocument(xmlFileName).getRootElement();
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
