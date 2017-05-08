package com.tegeltech.jenkinscommits.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Status {

    /** Status of the build, e.g. SUCCESS, UNSTABLE, FAILURE. */
    private String result;
}
