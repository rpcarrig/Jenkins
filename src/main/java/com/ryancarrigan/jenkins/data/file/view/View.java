package com.ryancarrigan.jenkins.data.file.view;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suave Peanut on 5/22/14.
 */
public class View extends JenkinsXMLFile {
    private final String description;
    private final List<ViewJob> jobs;

    public View(final Document document) {
        super(document, "listView");
        this.description = root.getChildText("description");
        this.jobs = getJobList();
    }

    private List<ViewJob> getJobList() {
        final List<ViewJob> jobs = new ArrayList<ViewJob>();
        for (final Element job : root.getChildren("job")) {
            jobs.add(new ViewJob(job));
        }
        return jobs;
    }

    public String getDescription() {
        return description;
    }

    public List<ViewJob> getJobs() {
        return jobs;
    }

}
