package com.ryancarrigan.jenkins.data.file.home;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import com.ryancarrigan.jenkins.data.file.view.View;
import org.jdom2.Element;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class HomeView extends JenkinsXMLFile {
    private String name;
    private String url;

    public HomeView(final Element view) {
        super(view, "view");
        this.name =  view.getChildText("name");
        this.url = view.getChildText("url");
    }

    public View getView(){
        return new View(getDocument(url));
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}