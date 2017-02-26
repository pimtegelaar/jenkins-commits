package com.tegeltech.jenkinscommits.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Configuration object that describes where to find the Jenkins Job.
 */
@Data
@AllArgsConstructor
public class JenkinsJob {

    /**
     * localhost, or some IP address.
     */
    private final String host;
    /**
     * The  port Jenkins is running on e.g. 8080
     */
    private final int port;

    /**
     * Name that was giving to the specific job in Jenkins.
     */
    private final String jobName;

    public JenkinsJob(String jobName) {
        this("localhost", 8080, jobName);
    }

    public String getUrl() {
        return "http://" + host + ":" + port + "/job/" + jobName;
    }

}
