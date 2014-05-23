package com.ryancarrigan.jenkins.data.file;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * com.ryancarrigan.jenkins.data.parsing
 *
 * @author Ryan P. Carrigan
 * @since 5/22/14.
 */
public class Job extends JenkinsXMLFile {
    private Boolean buildable;
    private Boolean concurrentBuild;
    private Boolean inQueue;
    private Boolean keepDependencies;
    private Build firstBuild;
    private Build lastBuild;
    private Build lastCompletedBuild;
    private Build lastStableBuild;
    private Build lastSuccessfulBuild;
    private Build lastUnstableBuild;
    private Build lastUnsuccessfulBuild;
    private Integer nextBuildNumber;
    private List<Build> builds;
    private List<HealthReport> healthReports;
    private List<Module> modules;
    private String color;
    private String displayName;
    private String name;
    private String url;

    public Job(final Document document) {
        super(document);
        this.buildable = Boolean.valueOf(root.getChildText("buildable"));
        this.concurrentBuild = Boolean.valueOf(root.getChildText("concurrentBuild"));
        this.inQueue = Boolean.valueOf(root.getChildText("inQueue"));
        this.keepDependencies = Boolean.valueOf(root.getChildText("keepDependencies"));
        this.firstBuild = getBuild(root.getChild("firstBuild"));
        this.lastBuild = getBuild(root.getChild("lastBuild"));
        this.lastCompletedBuild = getBuild(root.getChild("lastCompletedBuild"));
        this.lastStableBuild = getBuild(root.getChild("lastStableBuild"));
        this.lastSuccessfulBuild = getBuild(root.getChild("lastSuccessfulBuild"));
        this.lastUnstableBuild = getBuild(root.getChild("lastUnstableBuild"));
        this.lastUnsuccessfulBuild = getBuild(root.getChild("lastUnsuccessfulBuild"));
        this.nextBuildNumber = Integer.valueOf(root.getChildText("nextBuildNumber"));
        this.builds = getBuildList(root);
        this.healthReports = getHealthReportList(root);
        this.modules = getModuleList(root);
        this.color = root.getChildText("color");
        this.displayName = root.getChildText("displayName");
        this.name = root.getChildText("name");
        this.url = root.getChildText("url");
    }

    public Boolean getBuildable() {
        return buildable;
    }

    public Boolean getConcurrentBuild() {
        return concurrentBuild;
    }

    public Boolean getInQueue() {
        return inQueue;
    }

    public Boolean getKeepDependencies() {
        return keepDependencies;
    }

    public Build getFirstBuild() {
        return firstBuild;
    }

    public Build getLastBuild() {
        return lastBuild;
    }

    public Build getLastCompletedBuild() {
        return lastCompletedBuild;
    }

    public Build getLastStableBuild() {
        return lastStableBuild;
    }

    public Build getLastSuccessfulBuild() {
        return lastSuccessfulBuild;
    }

    public Build getLastUnstableBuild() {
        return lastUnstableBuild;
    }

    public Build getLastUnsuccessfulBuild() {
        return lastUnsuccessfulBuild;
    }

    public Integer getNextBuildNumber() {
        return nextBuildNumber;
    }

    public List<Build> getBuilds() {
        return builds;
    }

    public List<HealthReport> getHealthReports() {
        return healthReports;
    }

    public List<Module> getModules() {
        return modules;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    private Build getBuild(final Element build) {
        return new Build(build);
    }

    private List<Build> getBuildList(final Element root) {
        final List<Build> builds = new ArrayList<Build>();
        for (final Element build : root.getChildren("build")) {
            builds.add(getBuild(build));
        }
        return builds;
    }

    private List<HealthReport> getHealthReportList(final Element root) {
        final List<HealthReport> healthReports = new ArrayList<HealthReport>();
        for (final Element healthReport : root.getChildren("healthReport")) {
            healthReports.add(new HealthReport(healthReport));
        }
        return healthReports;
    }

    private List<Module> getModuleList(final Element root) {
        final List<Module> modules = new ArrayList<Module>();
        for (final Element module : root.getChildren("module")) {
            modules.add(new Module(module));
        }
        return modules;
    }

    private class Build {
        private Integer number;
        private String url;

        protected Build(final Element build) {
            this.number = Integer.valueOf(build.getChildText("number"));
            this.url = build.getChildText("url");
        }
    }

    private class HealthReport {
        private Integer score;
        private String description;
        private String iconUrl;

        protected HealthReport(final Element healthReport) {
            this.score = Integer.valueOf(healthReport.getChildText("score"));
            this.description = healthReport.getChildText("description");
            this.iconUrl = healthReport.getChildText("iconUrl");
        }
    }

    private class Module {
        private String color;
        private String displayName;
        private String name;
        private String url;

        protected Module(final Element module) {
            this.color = module.getChildText("color");
            this.displayName = module.getChildText("displayName");
            this.name = module.getChildText("name");
            this.url = module.getChildText("url");
        }
    }
}
