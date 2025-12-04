package com.challenge.elowen.artifact_clue_detector.controller;

import com.challenge.elowen.artifact_clue_detector.repository.ManuscriptRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StatsController {

    private final ManuscriptRepository repository;

    public StatsController(ManuscriptRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        long countClueFound = repository.countByHasClueTrue();
        long countNoClue = repository.countByHasClueFalse();

        double ratio = (countClueFound + countNoClue) == 0
                ? 0.0
                : (double) countClueFound / (countClueFound + countNoClue);

        Map<String, Object> stats = new HashMap<>();
        stats.put("count_clue_found", countClueFound);
        stats.put("count_no_clue", countNoClue);
        stats.put("ratio", ratio);

        return stats;
    }
}