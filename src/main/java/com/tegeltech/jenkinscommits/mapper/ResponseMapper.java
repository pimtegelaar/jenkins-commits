package com.tegeltech.jenkinscommits.mapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tegeltech.jenkinscommits.domain.BuildDuration;
import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import com.tegeltech.jenkinscommits.domain.LatestBuild;
import com.tegeltech.jenkinscommits.domain.Status;
import com.tegeltech.jenkinscommits.exception.InvalidXmlResponseException;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class ResponseMapper {

    private final XmlMapper mapper;

    public CommitsResponse parseCommits(String response) {
        return map(response, CommitsResponse.class);
    }

    public int getLastBuildNumber(String response) {
        LatestBuild latestBuild = map(response, LatestBuild.class);
        return latestBuild.getNumber();
    }

    public int getDuration(String response) {
        BuildDuration buildDuration = map(response, BuildDuration.class);
        return buildDuration.getDuration();
    }

    public String getStatus(String response) {
        Status status = map(response, Status.class);
        return status.getResult();
    }

    private <T> T map(String response, Class<T> clazz) {
        try {
            return mapper.readValue(response, clazz);
        } catch (IOException e) {
            throw new InvalidXmlResponseException("Something went wrong while parsing the response xml", e);
        }
    }
}
