package com.ryancarrigan.jenkins.data.jenkins.build.changeset;

import org.jdom2.Element;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class Item {

    private final ItemAuthor author;
    private final ItemPath path;
    private final Long commitId;
    private final Long revision;
    private final Long timestamp;
    private final String affectedPath;
    private final String date;
    private final String msg;
    private final String user;

    public Item(final Element item) {
        this.author = new ItemAuthor(item.getChild("author"));
        this.commitId = Long.valueOf(item.getChildText("commitId"));
        this.revision = Long.valueOf(item.getChildText("revision"));
        this.timestamp = Long.valueOf(item.getChildText("timestamp"));
        this.affectedPath = item.getChildText("affectedPath");
        this.date = item.getChildText("date");
        this.msg = item.getChildText("msg");
        this.user = item.getChildText("user");
        this.path = new ItemPath(item.getChild("path"));
    }

    public ItemAuthor getAuthor() {
        return author;
    }

    public Long getCommitId() {
        return commitId;
    }

    public Long getRevision() {
        return revision;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getAffectedPath() {
        return affectedPath;
    }

    public String getDate() {
        return date;
    }

    public String getMsg() {
        return msg;
    }

    public String getUser() {
        return user;
    }

    public ItemPath getPath() {
        return path;
    }
}
