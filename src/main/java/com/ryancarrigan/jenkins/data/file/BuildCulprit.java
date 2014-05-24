package com.ryancarrigan.jenkins.data.file;

import org.jdom2.Element;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class BuildCulprit {
    private final String absoluteUrl;
    private final String fullName;

    public BuildCulprit(final Element culprit) {
        this.absoluteUrl = culprit.getChildText("absoluteUrl");
        this.fullName = culprit.getChildText("fullName");
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public String getFullName() {
        return fullName;
    }
}
