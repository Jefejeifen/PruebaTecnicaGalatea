package com.challenge.elowen.artifact_clue_detector.acceptance_test.karate.stats;

import com.intuit.karate.junit5.Karate;

public class StatsRunner {

    @Karate.Test
    Karate statsRunner() {
        return Karate.run("classpath:karate/stats/stats.feature");
    }


}
