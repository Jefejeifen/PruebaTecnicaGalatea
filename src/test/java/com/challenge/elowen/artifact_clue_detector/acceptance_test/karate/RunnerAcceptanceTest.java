package com.challenge.elowen.artifact_clue_detector.acceptance_test.karate;

import com.intuit.karate.junit5.Karate;

public class RunnerAcceptanceTest {

    @Karate.Test
    Karate runAllTests() {
        return Karate.run("classpath:karate").tags("~@ignore");
    }

}
