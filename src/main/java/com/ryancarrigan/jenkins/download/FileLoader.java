package com.ryancarrigan.jenkins.download;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;

/**
 * com.ryancarrigan.jenkins
 *
 * @author Ryan P. Carrigan
 * @since 5/23/14.
 */
public class FileLoader {
    private final String filePath;

    public FileLoader(final String filePath) {
        this.filePath = filePath;
    }

    public Document getXmlDocument() {
        try {
            return new SAXBuilder().build(filePath);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Error getting document");
    }
}
