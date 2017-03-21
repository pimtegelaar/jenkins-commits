package com.tegeltech.jenkinscommits;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import com.tegeltech.jenkinscommits.domain.Changes;
import com.tegeltech.jenkinscommits.domain.JenkinsJob;
import com.tegeltech.jenkinscommits.http.HttpClient;
import com.tegeltech.jenkinscommits.mapper.CommitsResponseMapper;
import com.tegeltech.jenkinscommits.mapper.ResponseMapper;
import okhttp3.OkHttpClient;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String jobName = "coveragedemo";
        JenkinsJob jenkinsJob = new JenkinsJob(jobName);

        HttpClient httpClient = new HttpClient(new OkHttpClient.Builder().build());
        JenkinsApiClient jenkinsApiClient = new JenkinsApiClient(httpClient);

        String latestBuildResponse = jenkinsApiClient.getLatestBuildNumber(jenkinsJob);
        ResponseMapper responseParser = new ResponseMapper(new XmlMapper());
        int latestBuildNumber = 8;// responseParser.getLastBuildNumber(latestBuildResponse);

        String commitsResponse = jenkinsApiClient.fetchCommits(jenkinsJob, latestBuildNumber);
        CommitsResponse changes = responseParser.parseCommits(commitsResponse);

        CommitsResponseMapper commitsResponseMapper = new CommitsResponseMapper();
        if (changes.getAffectedPath() == null)
            return;

        Changes input = commitsResponseMapper.parse(changes);

        System.out.println();
        System.out.println("---------------------");
        System.out.println("       Sources       ");
        System.out.println("---------------------");
        input.getChangedSources().forEach(System.out::println);

        System.out.println();
        System.out.println("---------------------");
        System.out.println("        Tests        ");
        System.out.println("---------------------");
        input.getChangedTests().forEach(System.out::println);
    }

}
