package com.tegeltech.jenkinscommits;

import com.tegeltech.jenkinscommits.domain.JenkinsJob;
import com.tegeltech.jenkinscommits.http.HttpClient;

public class JenkinsApiClient {

    private HttpClient httpClient;

    public JenkinsApiClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public String fetchCommits(JenkinsJob jenkinsJob, int buildNumber) {
        String url = jenkinsJob.getUrl() + "/" + buildNumber + "/api/xml?wrapper=CommitsResponse&xpath=//changeSet//affectedPath";
        return httpClient.get(url);
    }

    public String getLatestBuildNumber(JenkinsJob jenkinsJob) {
        String url = jenkinsJob.getUrl() + "/api/xml?wrapper=LatestBuild&xpath=//lastBuild//number";
        return httpClient.get(url);
    }

}
