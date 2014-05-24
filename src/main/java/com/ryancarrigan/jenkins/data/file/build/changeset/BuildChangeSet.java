package com.ryancarrigan.jenkins.data.file.build.changeset;

import org.jdom2.Element;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class BuildChangeSet {

    private final Item item;
    private final String kind;

    public BuildChangeSet(final Element changeSet) {
        this.item = new Item(changeSet.getChild("item"));
        this.kind = changeSet.getChildText("kind");
    }

    public Item getItem() {
        return item;
    }

    public String getKind() {
        return kind;
    }
}
