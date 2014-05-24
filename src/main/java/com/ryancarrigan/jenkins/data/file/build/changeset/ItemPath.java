package com.ryancarrigan.jenkins.data.file.build.changeset;

import org.jdom2.Element;

/**
 * Created by Suave Peanut on 2014.5.23.
 */
public class ItemPath {
    private final String editType;
    private final String file;

    protected ItemPath(final Element path) {
        this.editType = path.getChildText("editType");
        this.file = path.getChildText("file");
    }

    public String getEditType() {
        return editType;
    }

    public String getFile() {
        return file;
    }
}
