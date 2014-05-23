package com.ryancarrigan.jenkins.data.file;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suave Peanut on 5/22/14.
 */
public class Build extends JenkinsXMLFile {
    private Boolean building;
    private Boolean keepLog;
    private ChangeSet changeSet;
    private Integer failCount;
    private Integer number;
    private Integer skipCount;
    private Integer totalCount;
    private List<Culprit> culprits;
    private Long duration;
    private Long estimatedDuration;
    private Long timestamp;
    private String builtOn;
    private String cause;
    private String fullDisplayName;
    private String mavenArtifacts;
    private String mavenVersionUsed;
    private String id;
    private String result;
    private String url;

    public Build(final Document document) {
        super(document, "mavenModuleSetBuild");
        this.building = Boolean.valueOf(root.getChildText("building"));
        this.keepLog = Boolean.valueOf(root.getChildText("keepLog"));
        this.changeSet = new ChangeSet(root.getChild("changeSet"));
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

    private List<Culprit> getCulpritList() {
        final List<Culprit> culprits = new ArrayList<Culprit>();
        for (final Element culprit : root.getChildren("culprit")) {
            culprits.add(new Culprit(culprit));
        }
        return culprits;
    }

    public Boolean getBuilding() {
        return building;
    }

    public Boolean getKeepLog() {
        return keepLog;
    }

    public ChangeSet getChangeSet() {
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

    public List<Culprit> getCulprits() {
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

    private class ChangeSet {
        private Item item;
        private String kind;
        
        protected ChangeSet(final Element changeSet) {
            log.info(changeSet.getText());
            this.item = new Item(changeSet.getChild("item"));
            this.kind = changeSet.getChildText("kind");
        }

        private class Item {
            private Author author;
            private Long commitId;
            private Long revision;
            private Long timestamp;
            private String affectedPath;
            private String date;
            private String msg;
            private String user;
            
            protected Item(final Element item) {
                this.author = new Author(item.getChild("author"));
                this.commitId = Long.valueOf(item.getChildText("commitId"));
                this.revision = Long.valueOf(item.getChildText("revision"));
                this.timestamp = Long.valueOf(item.getChildText("timestamp"));
                this.affectedPath = item.getChildText("affectedPath");
                this.date = item.getChildText("date");
                this.msg = item.getChildText("msg");
                this.user = item.getChildText("user");
            }
            
            private class Author {
                private String abosluteUrl;
                private String fullName;
                
                protected Author(final Element author) {
                    this.abosluteUrl = author.getChildText("absoluteUrl");
                    this.fullName = author.getChildText("fullName");
                }
            }
            
            private class Path {
                private String editType;
                private String file;
                
                protected Path(final Element path) {
                    this.editType = path.getChildText("editType");
                    this.file = path.getChildText("file");
                }
            }
        }
    }

    private class Culprit {
        private String absoluteUrl;
        private String fullName;

        protected Culprit(final Element culprit) {
            this.absoluteUrl = culprit.getChildText("absoluteUrl");
            this.fullName = culprit.getChildText("fullName");
        }
    }
}
