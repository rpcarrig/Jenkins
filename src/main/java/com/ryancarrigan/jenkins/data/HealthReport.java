package com.ryancarrigan.jenkins.data;

/**
 * Created by Suave Peanut on 5/22/14.
 */
public class HealthReport {
    private Integer score;
    private String description;
    private String iconUrl;

    public HealthReport(final String description, final String iconUrl, final Integer score) {
        this.description = description;
        this.iconUrl = iconUrl;
        this.score = score;
    }
}
