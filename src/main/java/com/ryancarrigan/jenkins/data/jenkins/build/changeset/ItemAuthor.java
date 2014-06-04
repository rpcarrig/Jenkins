package com.ryancarrigan.jenkins.data.jenkins.build.changeset;

import org.jdom2.Element;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class ItemAuthor {
    private final String abosluteUrl;
    private final String fullName;

    public ItemAuthor(final Element author) {
        this.abosluteUrl = author.getChildText("absoluteUrl");
        this.fullName = author.getChildText("fullName");
    }

    public String getAbosluteUrl() {
        return abosluteUrl;
    }

    public String getFullName() {
        return fullName;
    }
}
