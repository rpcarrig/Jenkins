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
import java.math.BigDecimal;

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
    private String remoteFile;

    public FileDownloader(final String url) {
        this(url, API.XML, Depth.ZERO);
    }

    public FileDownloader(final String url, final API api, final Depth depth) {
        StringBuilder buffer = new StringBuilder(url);
        if (!url.endsWith("/"))
            buffer.append("/");
        this.remoteFile = String.format("%sapi/%s?depth=%d", buffer.toString(), api.getApi(), depth.getDepth());
        log.info(remoteFile);
    }

    public String getFileSize() {
        final Double kb = new BigDecimal(bytesRead).divide(new BigDecimal(1024)).doubleValue();
        return String.format("%d KB", kb.intValue());
    }

    public File downloadToFile(final String destination) {
        File file = new File(destination);
        InputStream inputStream = getInputStream(remoteFile);
        try {
            BufferedOutputStream outputStream = getOutputStream(file);
            Integer input;
            while ((input = (inputStream.read())) >= 0) {
                outputStream.write(input);
                bytesRead += input;
            }
            outputStream.close();
            inputStream.close();
            log.info(String.format("Reached end of file. File size: %d", bytesRead));
            return file;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        throw new NullPointerException("No file could be returned");
    }

    public Integer getBytesRead() {
        return bytesRead;
    }

    private static BufferedInputStream getInputStream(final String url){
        log.info("Executing HTTP request...");
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
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Could not get output file.");
    }

    public enum API {
        XML("xml"),
        JSON("json"),
        PYTHON("python");

        private String api;
        private API(final String api) {
            this.api = api;
        }

        protected String getApi() {
            return api;
        }
    }

    public enum Depth {
        ZERO(0),
        ONE(1),
        TWO(2);

        private Integer depth;
        private Depth(final Integer depth) {
            this.depth = depth;
        }

        protected Integer getDepth() {
            return depth;
        }
    }

}
