package com.ryancarrigan.jenkins.xml;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;

/**
 * com.ryancarrigan.jenkins.xml
 *
 * @author Ryan P. Carrigan
 * @since 5/22/14.
 */
public abstract class XMLParser {
    protected Document document;
    protected Element rootNode;

    public XMLParser(final Document document) {
        this.document = document;
        this.rootNode = document.getRootElement();
    }

    public XMLParser(final String xmlFile) {
        try {
            document = new SAXBuilder().build(xmlFile);
            rootNode = document.getRootElement();
        } catch (final JDOMException jde) {
            jde.printStackTrace();
        } catch (final IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
