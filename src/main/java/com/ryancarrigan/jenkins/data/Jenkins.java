package com.ryancarrigan.jenkins.data;

import com.ryancarrigan.jenkins.data.file.home.Home;
import com.ryancarrigan.jenkins.download.FileDownloader;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class Jenkins extends FileDownloader {
    protected String serverUrl;

    public Jenkins(final String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Home getHome() {
        return new Home(getDocument(serverUrl));
    }
}
