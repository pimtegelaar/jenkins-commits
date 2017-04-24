package com.tegeltech.jenkinscommits.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuildDuration {

    /** The duration in milliseconds that it took to finish the build. */
    public int duration;
}
