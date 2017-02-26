package com.tegeltech.jenkinscommits.domain;

import lombok.Data;

/** Configuration object that describes where to find the Jenkins Job. */
@Data
public class JenkinsJob {

    /** localhost, or some IP address. */
    private final String host;
    /** The  port Jenkins is running on e.g. 8080 */
    private final int  port;

    /** Name that was giving to the specific job in Jenkins. */
    private final String jobName;

    public String getUrl() {
        return "http://" + host + ":" + port + "/job/" + jobName;
    }

}
