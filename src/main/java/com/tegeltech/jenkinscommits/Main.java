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
        String host = "localhost";
        int port = 8080;
        String jobName = "commons-io";
        JenkinsJob jenkinsJob = new JenkinsJob(host, port, jobName);

        HttpClient httpClient = new HttpClient(new OkHttpClient.Builder().build());
        JenkinsApiClient jenkinsApiClient = new JenkinsApiClient(httpClient);

        String latestBuildResponse = jenkinsApiClient.getLatestBuildNumber(jenkinsJob);
        ResponseMapper responseParser = new ResponseMapper(new XmlMapper());
        int latestBuildNumber = responseParser.getLastBuildNumber(latestBuildResponse);

        String commitsResponse = jenkinsApiClient.fetchCommits(jenkinsJob, latestBuildNumber);
        CommitsResponse changes = responseParser.parseCommits(commitsResponse);

        List<String> sourceDirs = Collections.singletonList("src/main/java/");
        List<String> testDirs = Collections.singletonList("src/test/java/");

        CommitsResponseMapper commitsResponseMapper = new CommitsResponseMapper(sourceDirs, testDirs);
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
