package com.tegeltech.jenkinscommits.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

/** Contains the changes a.k.a. the paths to the changed files (sources/tests). */
@Data
public class CommitsResponse {

    @JacksonXmlElementWrapper(useWrapping = false)
    private  String[] affectedPath;
}
