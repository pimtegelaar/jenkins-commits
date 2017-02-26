package com.tegeltech.jenkinscommits.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/** The changed sources and tests in a specific build. */
@Data
public class Changes {

    private final List<String> changedSources = new ArrayList<>();
    private final List<String> changedTests = new ArrayList<>();

    public void addSource(String source) {
        changedSources.add(source);
    }

    public void addTest(String test) {
        changedTests.add(test);
    }
}
