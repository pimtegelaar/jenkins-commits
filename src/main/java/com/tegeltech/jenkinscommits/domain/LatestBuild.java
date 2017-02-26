package com.tegeltech.jenkinscommits.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LatestBuild {

    /** The number of the latest Jenkins build (starts at 1 and is incremented by 1 each build). */
    public int number;
}
