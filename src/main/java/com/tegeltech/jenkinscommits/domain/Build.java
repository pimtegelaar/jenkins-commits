package com.tegeltech.jenkinscommits.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Build {
    private int buildNumber;
    private int duration;
    private String status;
    private Changes changes;
}
