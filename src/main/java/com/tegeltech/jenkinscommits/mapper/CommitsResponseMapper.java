package com.tegeltech.jenkinscommits.mapper;

import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import com.tegeltech.jenkinscommits.domain.Changes;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class CommitsResponseMapper {

    private final List<String> sourceDirs;
    private final List<String> testSourceDirs;

    /**
     * Parses the commitsResponse and determines for each affected path if it is a source file, a test, or other.
     */
    public Changes parse(CommitsResponse commitsResponse) {
        Changes changes = new Changes();
        Arrays.stream(commitsResponse.getAffectedPath()).forEach(path -> {
            if (isSource(path)) {
                changes.addSource(path);
            } else if (isTest(path)) {
                changes.addTest(path);
            }
        });
        return changes;
    }

    private boolean isSource(String path) {
        return sourceDirs.stream().anyMatch(path::startsWith);
    }

    private boolean isTest(String path) {
        return testSourceDirs.stream().anyMatch(path::startsWith);
    }

}
