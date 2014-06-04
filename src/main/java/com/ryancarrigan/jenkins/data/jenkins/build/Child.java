package com.ryancarrigan.jenkins.data.jenkins.build;

import com.ryancarrigan.jenkins.data.jenkins.build.changeset.ChangeSet;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * com.ryancarrigan.jenkins.data.jenkins.build.build
 *
 * @author Ryan P. Carrigan
 * @since 6/1/14.
 */
public class Child {
    private final Boolean building;
    private final Boolean keepLog;
    private final BuildCulprit culprit;
    private final ChangeSet changeSet;
    private final Integer failCount;
    private final Integer number;
    private final Integer skipCount;
    private final Integer totalCount;
    private final List<ChildArtifact> artifacts;
    private final Long duration;
    private final Long estimatedDuration;
    private final Long timestamp;
    private final String builtOn;
    private final String fullDisplayName;
    private final String id;
    private final String mavenArtifactsUrl;
    private final String result;
    private final String url;

    public Child(final Element child) {
        this.artifacts = getArtifacts(child);
        this.building = Boolean.valueOf(child.getChildText("building"));
        this.builtOn = child.getChildText("builtOn");
        this.changeSet = new ChangeSet(child.getChild("changeset"));
        this.culprit = new BuildCulprit(child.getChild("culprit"));
        this.duration = Long.valueOf(child.getChildText("duration"));
        this.estimatedDuration = Long.valueOf(child.getChildText("estimatedDuration"));
        this.failCount = Integer.valueOf(getActionChildText(child, "failCount"));
        this.fullDisplayName = child.getChildText("fullDisplayName");
        this.id = child.getChildText("id");
        this.keepLog = Boolean.valueOf(child.getChildText("keepLog"));
        this.mavenArtifactsUrl = child.getChild("mavenArtifacts").getChildText("url");
        this.number = Integer.valueOf(child.getChildText("number"));
        this.result = child.getChildText("result");
        this.skipCount = Integer.valueOf(getActionChildText(child, "skipCount"));
        this.timestamp = Long.valueOf(child.getChildText("timestamp"));
        this.totalCount = Integer.valueOf(getActionChildText(child, "totalCount"));
        this.url = child.getChildText("url");
    }

    private List<ChildArtifact> getArtifacts(final Element child) {
        final List<ChildArtifact> artifacts = new ArrayList<>();
        for (final Element artifact : child.getChildren("artifact")) {
            artifacts.add(new ChildArtifact(artifact));
        }
        return artifacts;
    }

    private String getActionChildText(final Element root, final String child) {
        for (final Element action : root.getChildren("action")) {
            final Element descendant = action.getChild(child);
            if (null != descendant)
                return descendant.getText();
        }
        throw new NullPointerException("Unable to identify child");
    }
}
