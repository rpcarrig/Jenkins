package com.ryancarrigan.jenkins.data.file;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suave Peanut on 5/22/14.
 */
public class View extends JenkinsXMLFile {
    private String description;
    private List<Job> jobs;

    public View(final Document document) {
        super(document, "listView");
        this.description = root.getChildText("description");
        this.jobs = getJobList();
    }

    private List<Job> getJobList() {
        List<Job> jobs = new ArrayList<Job>();
        for (final Element job : root.getChildren("job")) {
            jobs.add(new Job(job));
        }
        return jobs;
    }

    public String getDescription() {
        return description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public class Job {
        private String color;
        private String name;
        private String url;

        protected Job(final Element job) {
            this.color = job.getChildText("color");
            this.name = job.getChildText("name");
            this.url = job.getChildText("url");
        }

        public String getColor() {
            return color;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return String.format("(%s) %s [%s]", color, name, url);
        }
    }
}
