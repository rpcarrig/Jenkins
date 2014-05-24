package com.ryancarrigan.jenkins.data.file.job;

import com.ryancarrigan.jenkins.data.JenkinsXMLFile;
import com.ryancarrigan.jenkins.data.file.job.HealthReport;
import com.ryancarrigan.jenkins.data.file.job.JobBuild;
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
    private final Boolean buildable;
    private final Boolean concurrentBuild;
    private final Boolean inQueue;
    private final Boolean keepDependencies;
    private final Integer nextBuildNumber;
    private final JobBuild firstBuild;
    private final JobBuild lastBuild;
    private final JobBuild lastCompletedBuild;
    private final JobBuild lastStableBuild;
    private final JobBuild lastSuccessfulBuild;
    private final JobBuild lastUnstableBuild;
    private final JobBuild lastUnsuccessfulBuild;
    private final List<HealthReport> healthReports;
    private final List<JobBuild> builds;
    private final List<Module> modules;
    private final String color;
    private final String displayName;
    private final String name;
    private final String url;

    public Job(final Document document) {
        super(document, "mavenModuleSet");
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

    @Override
    public String toString() {
        return String.format("%s (%s)", displayName, name);
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

    public JobBuild getFirstBuild() {
        return firstBuild;
    }

    public JobBuild getLastBuild() {
        return lastBuild;
    }

    public JobBuild getLastCompletedBuild() {
        return lastCompletedBuild;
    }

    public JobBuild getLastStableBuild() {
        return lastStableBuild;
    }

    public JobBuild getLastSuccessfulBuild() {
        return lastSuccessfulBuild;
    }

    public JobBuild getLastUnstableBuild() {
        return lastUnstableBuild;
    }

    public JobBuild getLastUnsuccessfulBuild() {
        return lastUnsuccessfulBuild;
    }

    public Integer getNextBuildNumber() {
        return nextBuildNumber;
    }

    public List<JobBuild> getBuilds() {
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

    private JobBuild getBuild(final Element build) {
        return new JobBuild(build);
    }

    private List<JobBuild> getBuildList(final Element root) {
        final List<JobBuild> builds = new ArrayList<JobBuild>();
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

}
