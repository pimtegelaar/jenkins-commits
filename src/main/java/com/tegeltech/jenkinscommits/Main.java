package com.tegeltech.jenkinscommits;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tegeltech.jenkinscommits.domain.Build;
import com.tegeltech.jenkinscommits.domain.Changes;
import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import com.tegeltech.jenkinscommits.domain.JenkinsJob;
import com.tegeltech.jenkinscommits.http.HttpClient;
import com.tegeltech.jenkinscommits.mapper.CommitsResponseMapper;
import com.tegeltech.jenkinscommits.mapper.ResponseMapper;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String jobName = "joda-time-success";
        if (args.length > 0)
            jobName = args[0];
        JenkinsJob jenkinsJob = new JenkinsJob(jobName);

        HttpClient httpClient = new HttpClient(new OkHttpClient.Builder().build());
        JenkinsApiClient jenkinsApiClient = new JenkinsApiClient(httpClient);

        String latestBuildResponse = jenkinsApiClient.getLatestBuildNumber(jenkinsJob);
        ResponseMapper responseParser = new ResponseMapper(new XmlMapper());
        int latestBuildNumber = responseParser.getLastBuildNumber(latestBuildResponse);

        System.out.println("Latest build  number: " + latestBuildResponse);
        List<Build> builds = new ArrayList<>();

        for (int currentBuildNumber = 1; currentBuildNumber <= latestBuildNumber; currentBuildNumber++) {
            System.out.println("");
            System.out.println("-------------------");
            System.out.println("Build  number: " + currentBuildNumber);
            String durationResponse = jenkinsApiClient.getDuration(jenkinsJob, currentBuildNumber);
            int duration = responseParser.getDuration(durationResponse);
            System.out.println("Build duration: " + duration);

            String statusResponse = jenkinsApiClient.getStatus(jenkinsJob, currentBuildNumber);
            String status = responseParser.getStatus(statusResponse);
            System.out.println("Build status: " + status);

            String commitsResponse = jenkinsApiClient.fetchCommits(jenkinsJob, currentBuildNumber);
            CommitsResponse changes = responseParser.parseCommits(commitsResponse);

            CommitsResponseMapper commitsResponseMapper = new CommitsResponseMapper();
            if (changes.getAffectedPath() == null)
                continue;

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

            builds.add(new Build(currentBuildNumber, duration, status, input));
        }
        System.out.println("| # | Duration | Sources | Tests | Total  | Status |");
        System.out.println("|---|----------|---------|-------|--------|--------|");
        for (Build build : builds) {
            Changes changes = build.getChanges();
            List<String> changedSources = changes.getChangedSources();
            List<String> changedTests = changes.getChangedTests();
            int totalChanges = changedSources.size() + changedTests.size();
            System.out.println("| " + build.getBuildNumber()
                    + " | " + build.getDuration()
                    + " | " + changedSources.size()
                    + " | " + changedTests.size()
                    + " | " + totalChanges
                    + " | " + build.getStatus()
                    + " |");
        }
        System.out.println("done");
    }

}
