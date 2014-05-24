package com.ryancarrigan.jenkins.download;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * com.ryancarrigan.jenkins.build
 *
 * @author Ryan P. Carrigan
 * @since 5/22/14.
 */
public class FileDownloader {
    private File outputFile;
    private final static String TEMP_FILE_NAME = "temp.xml";

    protected Logger log = LoggerFactory.getLogger(getClass());

    private String getApiRequest(final String url) {
        return String.format("%sapi/xml?depth=1", url);
    }

    protected Document getDocument(final String url) {
        final String file = getFile(getApiRequest(url));
        try {
            final Document document = new SAXBuilder().build(file);
            log.info(String.format("Returning <%s> document", document.getRootElement().getName()));
            return document;
        } catch (final JDOMException e) {
            log.error("JDOM Exception", e);
        } catch (IOException e) {
            log.error("IO Exception", e);
        } finally {
            outputFile.deleteOnExit();
        }
        return null;
    }

    private String getFile(final String remoteFile) {
        log.info(String.format("Downloading document: <%s>", remoteFile));
        outputFile = new File(TEMP_FILE_NAME);
        if (!outputFile.exists() && !outputFile.isDirectory()) {
            final InputStream inputStream = getInputStream(remoteFile);
            final BufferedOutputStream outputStream = getOutputStream(outputFile);
            try {
                if (outputFile.createNewFile())
                    throw new NullPointerException("Error creating file");

                Integer bytesIn;
                while ((bytesIn = inputStream.read()) >= 0)
                    outputStream.write(bytesIn);
                outputStream.close();
                inputStream.close();
            } catch (IOException ioe) {
                log.error("IO Exception", ioe);
            }
        }
        return outputFile.getName();
    }

    private BufferedInputStream getInputStream(final String url){
        log.info("Executing HTTP request...");
        try {
            HttpResponse response = HttpClients.createDefault().execute(new HttpGet(url));
            log.info("Received HTTP response");
            return new BufferedInputStream(response.getEntity().getContent());
        } catch (final ClientProtocolException e) {
            log.error("Client Protocol Exception", e);
        } catch (final IOException e) {
            log.error("IO Exception", e);
        }
        throw new NullPointerException("Could not get input file");
    }

    private BufferedOutputStream getOutputStream(final File file) {
        try {
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Could not get output file.");
    }

}
