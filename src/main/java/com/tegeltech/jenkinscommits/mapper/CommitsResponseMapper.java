package com.tegeltech.jenkinscommits.mapper;

import com.tegeltech.jenkinscommits.domain.CommitsResponse;
import com.tegeltech.jenkinscommits.domain.Changes;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class CommitsResponseMapper {

    private final List<String> sourceDirs;
    private final List<String> testSourceDirs;

    public CommitsResponseMapper() {
        this(Collections.singletonList("src/main/java/"),Collections.singletonList("src/test/java/"));
    }

    /**
     * Parses the commitsResponse and determines for each affected path if it is a source file, a test, or other.
     */
    public Changes parse(CommitsResponse commitsResponse) {
        Changes changes = new Changes();
        String[] affectedPath = commitsResponse.getAffectedPath();
        if(affectedPath != null) {
            Arrays.stream(affectedPath).forEach(path -> {
                if (isJava(path)) {
                    for (String dir : sourceDirs) {
                        if (path.startsWith(dir)) {
                            changes.addSource(javaPath(path, dir));
                        }
                    }
                    for (String dir : testSourceDirs) {
                        if (path.startsWith(dir)) {
                            changes.addTest(javaPath(path, dir));
                        }
                    }
                }
            });
        }
        return changes;
    }

    private boolean isJava(String path) {
        return path.endsWith(".java");
    }

    private String javaPath(String path, String dir) {
        return  path.replace(dir,"").replaceAll("^/","").replace("/",".").replaceAll("\\.java$","");
    }

}
