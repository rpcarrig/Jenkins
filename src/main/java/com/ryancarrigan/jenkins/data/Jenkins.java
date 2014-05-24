package com.ryancarrigan.jenkins.data;

import com.ryancarrigan.jenkins.data.file.home.Home;
import com.ryancarrigan.jenkins.data.file.view.View;
import com.ryancarrigan.jenkins.download.FileDownloader;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class Jenkins extends FileDownloader {
    private Home home;
    protected String serverUrl;

    public Jenkins(final String serverUrl) {
        this.home = new Home(getDocument(serverUrl));
        this.serverUrl = serverUrl;
    }

    public Home getHome() {
        return home;
    }

    public View getView(final String viewName) {
        return home.getView(viewName);
    }
}
