package com.ryancarrigan.jenkins.data.file;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * com.ryancarrigan.jenkins.data.file
 *
 * @author Ryan P. Carrigan
 * @since 5/23/14.
 */
public class Hudson extends JenkinsXMLFile {
    private Boolean quietingDown;
    private Boolean useCrumbs;
    private Boolean useSecurity;
    private Integer numExecutors;
    private Integer slaveAgentPort;
    private List<Job> jobs;
    private List<View> views;
    private String assignedLabel;
    private String mode;
    private String nodeDescription;
    private String nodeName;
    private String unlabeledLoad;
    private View primaryView;

    public Hudson(final Document xmlFile) {
        super(xmlFile, "hudson");
        this.quietingDown = Boolean.valueOf(root.getChildText("quietingDown"));
        this.useCrumbs = Boolean.valueOf(root.getChildText("useCrumbs"));
        this.useSecurity = Boolean.valueOf(root.getChildText("useSecurity"));
        this.numExecutors = Integer.valueOf(root.getChildText("numExecutors"));
        this.slaveAgentPort = Integer.valueOf(root.getChildText("slaveAgentPort"));
        this.jobs = getJobList();
        this.views = getViewList();
    }

    private List<Job> getJobList() {
        return new ArrayList<Job>();
    }

    private List<View> getViewList() {
        final List<View> viewList = new ArrayList<View>();
        for (final Element view : root.getChildren("view")) {
            viewList.add(new View(view));
        }
        return viewList;
    }

    public class View {
        private String name;
        private String url;

        protected View(final Element view) {
            this.name =  view.getChildText("name");
            this.url = view.getChildText("url");
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

    }
}
