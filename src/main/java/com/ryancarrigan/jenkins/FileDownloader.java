package com.ryancarrigan.jenkins;

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
    private static Logger log = LoggerFactory.getLogger(FileDownloader.class);

    private static BufferedInputStream inputStream = null;
    private Integer bytesRead = 0;

    public FileDownloader() {
        log.info("FileDownloader created");
    }

    public File downloadFile(final String source, final String destination) {
        File file = new File(destination);
        InputStream inputStream = getInputStream(source);
        log.info("Input stream open, ready to download");
        try {
            BufferedOutputStream outputStream = getOutputStream(file);
            Integer input;
            while ((input = (inputStream.read())) >= 0) {
                outputStream.write(input);
                bytesRead += input;
            }
            log.info("Reached end of file. Closing output stream");
            outputStream.close();
            log.info("Closing input stream");
            inputStream.close();
            log.info(String.format("File size: %d bytes", bytesRead));
            return file;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        throw new NullPointerException("No file could be returned");
    }

    public Document getDocument(final String url, final String fileName) {
        try {
            return new SAXBuilder().build(downloadFile(url, fileName));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Error getting document");
    }

    public Integer getBytesRead() {
        return bytesRead;
    }

    private static BufferedInputStream getInputStream(final String url){
        log.info("Getting input stream,executing HTTP request...");
        try {
            HttpResponse response = HttpClients.createDefault().execute(new HttpGet(url));
            log.info("Received HTTP response");
            return new BufferedInputStream(response.getEntity().getContent());
        } catch (ClientProtocolException e) {
            log.error("Exception", e);
        } catch (IOException e) {
            log.error("Exception", e);
        }
        throw new NullPointerException("Could not get input file");
    }

    private static BufferedOutputStream getOutputStream(final File file) {
        try {
            log.info("Creating and buffering output file.");
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Could not get output file.");
    }

}
