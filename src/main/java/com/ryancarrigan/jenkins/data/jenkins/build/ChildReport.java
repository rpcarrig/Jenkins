package com.ryancarrigan.jenkins.data.jenkins.build;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * com.ryancarrigan.jenkins.data.jenkins.build.build
 *
 * @author Ryan P. Carrigan
 * @since 6/1/14.
 */
public class ChildReport {
    private List<Child> children;

    public ChildReport(final Element childReport) {

    }

    private List<Child> getChildren(final Element childReport) {
        final List<Child> children = new ArrayList<>();
        for(final Element child : childReport.getChildren("child")) {
            children.add(new Child(child));
        }
        return children;
    }
}
