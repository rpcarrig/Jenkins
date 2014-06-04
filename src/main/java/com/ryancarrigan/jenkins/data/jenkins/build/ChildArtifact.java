package com.ryancarrigan.jenkins.data.jenkins.build;

import org.jdom2.Element;

/**
 * com.ryancarrigan.jenkins.data.jenkins.build.build
 *
 * @author Ryan P. Carrigan
 * @since 6/1/14.
 */
public class ChildArtifact  {
    private final String displayPath;
    private final String fileName;
    private final String relativePath;

    public ChildArtifact(final Element artifact) {
        this.displayPath = artifact.getChildText("displayPath");
        this.fileName = artifact.getChildText("fileName");
        this.relativePath = artifact.getChildText("relativePath");
    }

    public String getDisplayPath() {
        return displayPath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }
}
