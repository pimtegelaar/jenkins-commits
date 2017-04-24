package com.tegeltech.jenkinscommits.mapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tegeltech.jenkinscommits.domain.BuildDuration;
import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import com.tegeltech.jenkinscommits.domain.LatestBuild;
import com.tegeltech.jenkinscommits.exception.InvalidXmlResponseException;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class ResponseMapper {

    private final XmlMapper mapper;

    public CommitsResponse parseCommits(String commitsResponse) {
        try {
            return mapper.readValue(commitsResponse, CommitsResponse.class);
        } catch (IOException e) {
            throw new InvalidXmlResponseException("Something went wrong while parsing the response xml", e);
        }
    }


    public int getLastBuildNumber(String latestBuildResponse) {
        try {
            LatestBuild latestBuild = mapper.readValue(latestBuildResponse, LatestBuild.class);
            return latestBuild.getNumber();
        } catch (IOException e) {
            throw new InvalidXmlResponseException("Something went wrong while parsing the response xml", e);
        }
    }

    public int getDuration(String durationResponse) {
        try {
            BuildDuration buildDuration = mapper.readValue(durationResponse, BuildDuration.class);
            return buildDuration.getDuration();
        } catch (IOException e) {
            throw new InvalidXmlResponseException("Something went wrong while parsing the response xml", e);
        }
    }
}
