package com.ryancarrigan.jenkins.download;

import com.ryancarrigan.jenkins.data.file.home.Home;
import com.ryancarrigan.jenkins.data.file.view.View;
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
 * com.ryancarrigan.jenkins
 *
 * @author Ryan P. Carrigan
 * @since 5/23/14.
 */
public class JenkinsFileDownloader {
    private final static Logger log = LoggerFactory.getLogger(JenkinsFileDownloader.class);
    private final static String OUTPUT_DIR = "xml";
    private final static String SUFFIX = "api/xml?depth=1";
    private final String jenkinsUrl;

   public JenkinsFileDownloader(final String jenkinsUrl) {
       this.jenkinsUrl = jenkinsUrl;
   }

    public Home getPrimaryView() {
        return new Home(null);
    }

    public View getView(String viewName) {
        final String file = String.format("%s/view-%s.xml", OUTPUT_DIR, viewName.replace(' ', '-'));
        final String url = String.format("%sview/%s/%s", jenkinsUrl, viewName.replaceAll(" ", "%20"), SUFFIX);

        log.info(file);
        log.info(url);

        final Document docu = getDocument(getFile(file, url));
        return new View(docu);
    }

    private Document getDocument(final String localFile) {
        try {
            return new SAXBuilder().build(localFile);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFile(final String localFile, final String remoteFile) {
        final File outputFile = new File(localFile);
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
        return localFile;
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
}
