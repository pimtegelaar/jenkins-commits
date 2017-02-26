package com.tegeltech.jenkinscommits;

import com.tegeltech.jenkinscommits.domain.JenkinsJob;
import com.tegeltech.jenkinscommits.http.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsApiClientTest {

    private JenkinsApiClient jenkinsApiClient;

    @Mock
    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        jenkinsApiClient = new JenkinsApiClient(httpClient);
    }

    @Test
    public void fetchCommits() throws Exception {
        String host = "localhost";
        int port = 8080;
        String jobName = "commons-io";
        JenkinsJob jenkinsJob = new JenkinsJob(host, port, jobName);
        int buildNumber = 2;
        String responseString = "<CommitsResponse></CommitsResponse>";

        when(httpClient.get(any(String.class))).thenReturn(responseString);

        String result = jenkinsApiClient.fetchCommits(jenkinsJob, buildNumber);

        assertThat(result, is(responseString));
    }

    @Test
    public void getLatestBuildNumber() throws Exception {
        String host = "localhost";
        int port = 8080;
        String jobName = "commons-io";
        JenkinsJob jenkinsJob = new JenkinsJob(host, port, jobName);
        String buildNumber = "2";

        when(httpClient.get(any(String.class))).thenReturn(buildNumber);

        String result = jenkinsApiClient.getLatestBuildNumber(jenkinsJob);

        assertThat(result, is(buildNumber));
    }

}