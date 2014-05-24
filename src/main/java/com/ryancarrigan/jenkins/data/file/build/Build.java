package com.ryancarrigan.jenkins.data.file.build;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import com.ryancarrigan.jenkins.data.file.BuildCulprit;
import com.ryancarrigan.jenkins.data.file.build.changeset.BuildChangeSet;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suave Peanut on 5/22/14.
 */
public class Build extends JenkinsXMLFile {
    private final Boolean building;
    private final Boolean keepLog;
    private final BuildChangeSet changeSet;
    private final Integer failCount;
    private final Integer number;
    private final Integer skipCount;
    private final Integer totalCount;
    private final List<BuildCulprit> culprits;
    private final Long duration;
    private final Long estimatedDuration;
    private final Long timestamp;
    private final String builtOn;
    private final String cause;
    private final String fullDisplayName;
    private final String mavenArtifacts;
    private final String mavenVersionUsed;
    private final String id;
    private final String result;
    private final String url;

    public Build(final Document document) {
        super(document, "mavenModuleSetBuild");
        this.building = Boolean.valueOf(root.getChildText("building"));
        this.keepLog = Boolean.valueOf(root.getChildText("keepLog"));
        this.changeSet = new BuildChangeSet(root.getChild("changeSet"));
        this.failCount = Integer.valueOf(getActionChild("failCount").getText());
        this.number = Integer.valueOf(root.getChildText("number"));
        this.skipCount = Integer.valueOf(getActionChild("skipCount").getText());
        this.totalCount = Integer.valueOf(getActionChild("totalCount").getText());
        this.culprits = getCulpritList();
        this.duration = Long.valueOf(root.getChildText("duration"));
        this.estimatedDuration = Long.valueOf(root.getChildText("estimatedDuration"));
        this.timestamp = Long.valueOf(root.getChildText("timestamp"));
        this.builtOn = root.getChildText("builtOn");
        this.cause = getActionChild("cause").getChildText("shortDescription");
        this.fullDisplayName = root.getChildText("fullDisplayName");
        this.mavenArtifacts = root.getChildText("mavenArtifacts");
        this.mavenVersionUsed = root.getChildText("mavenVersionUsed");
        this.id = root.getChildText("id");
        this.result = root.getChildText("result");
        this.url = root.getChildText("url");
    }

    private Element getActionChild(final String child) {
        for (final Element action : root.getChildren("action")) {
            final Element descendant = root.getChild(child);
            if (null != descendant)
                return descendant;
        }
        throw new NullPointerException("Unable to identify child");
    }

    private List<BuildCulprit> getCulpritList() {
        final List<BuildCulprit> culprits = new ArrayList<BuildCulprit>();
        for (final Element culprit : root.getChildren("culprit")) {
            culprits.add(new BuildCulprit(culprit));
        }
        return culprits;
    }

    public Boolean getBuilding() {
        return building;
    }

    public Boolean getKeepLog() {
        return keepLog;
    }

    public BuildChangeSet getChangeSet() {
        return changeSet;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public List<BuildCulprit> getCulprits() {
        return culprits;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getEstimatedDuration() {
        return estimatedDuration;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getBuiltOn() {
        return builtOn;
    }

    public String getCause() {
        return cause;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public String getMavenArtifacts() {
        return mavenArtifacts;
    }

    public String getMavenVersionUsed() {
        return mavenVersionUsed;
    }

    public String getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public String getUrl() {
        return url;
    }
}
