package com.challenge.elowen.artifact_clue_detector.acceptance_test.karate.clue;

import com.intuit.karate.junit5.Karate;

public class ClueRunner {

    @Karate.Test
    Karate clueRunner() {
        return Karate.run("classpath:karate/clue/clue.feature");
    }


}
