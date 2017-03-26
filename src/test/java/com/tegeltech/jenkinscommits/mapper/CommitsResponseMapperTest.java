package com.tegeltech.jenkinscommits.mapper;

import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import com.tegeltech.jenkinscommits.domain.Changes;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CommitsResponseMapperTest {
    private CommitsResponseMapper commitsResponseMapper;

    @Before
    public void setUp() throws Exception {
        List<String> sourceDirs = Collections.singletonList("src/main/java");
        List<String> testSourceDirs = Collections.singletonList("src/test/java");

        commitsResponseMapper = new CommitsResponseMapper(sourceDirs, testSourceDirs);
    }

    @Test
    public void parse_addsSourcesToSourcesAndTestsToTests() throws Exception {
        String src1 = "src/main/java/org/apache/commons/io/FilenameUtils.java";
        String src2 = "src/main/java/org/apache/commons/io/serialization/package.html";
        String test1 = "src/test/java/org/apache/commons/io/monitor/AbstractMonitorTestCase.java";
        String test2 = "src/test/java/org/apache/commons/io/ByteOrderMarkTestCase.java";
        String test3 = "src/test/java/org/apache/commons/io/FileUtilsFileNewerTestCase.java";
        String other = "commitsResponse/commitsResponse.xml";
        String[] affectedPaths = {src1, src2, test1, test2, test3, other};
        CommitsResponse commitsResponse = new CommitsResponse();
        commitsResponse.setAffectedPath(affectedPaths);
        Changes result = commitsResponseMapper.parse(commitsResponse);

        List<String> changedSources = result.getChangedSources();
        assertThat(changedSources, hasItems("org.apache.commons.io.FilenameUtils"));
        assertThat(changedSources.size(), is(1));

        List<String> changedTests = result.getChangedTests();
        assertThat(changedTests, hasItems("org.apache.commons.io.monitor.AbstractMonitorTestCase",
                "org.apache.commons.io.ByteOrderMarkTestCase",
                "org.apache.commons.io.FileUtilsFileNewerTestCase"));
        assertThat(changedTests.size(), is(3));
    }

}