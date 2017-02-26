package com.tegeltech.jenkinscommits.mapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ResponseMapperTest {
    private ResponseMapper responseMapper;

    @Before
    public void setUp() throws Exception {
        responseMapper = new ResponseMapper(new XmlMapper());
    }

    @Test
    public void parseCommits() throws Exception {
        String src1 = "src/main/java/org/apache/commons/io/FilenameUtils.java";
        String src2 = "src/main/java/org/apache/commons/io/serialization/package.html";
        String test1 = "src/test/java/org/apache/commons/io/monitor/AbstractMonitorTestCase.java";
        String test2 = "src/test/java/org/apache/commons/io/ByteOrderMarkTestCase.java";
        String test3 = "src/test/java/org/apache/commons/io/FileUtilsFileNewerTestCase.java";
        String other = "changes/changes.xml";
        String comitsResponse = buildCommitResponse(src1,src2,test1,test2,test3,other);
        CommitsResponse result = responseMapper.parseCommits(comitsResponse);
        List<String> affectedPath = Arrays.asList(result.getAffectedPath());
        assertThat(affectedPath, hasItems(src1, src2, test1, test2, test3, other));
    }

    private String buildCommitResponse(String... paths) {
        StringBuilder response = new StringBuilder();
        response.append("<CommitsResponse>");
        Arrays.stream(paths).forEach(path -> response.append(String.format("<affectedPath>%s</affectedPath>", path)));
        response.append("</CommitsResponse>");
        return response.toString();
    }

    @Test
    public void getLastBuildNumber() throws Exception {
        String lastBuildResponse = "<LatestBuild><number>2</number></LatestBuild>";
        int lastBuildNumber = responseMapper.getLastBuildNumber(lastBuildResponse);
        assertThat(lastBuildNumber, is(2));
    }

}