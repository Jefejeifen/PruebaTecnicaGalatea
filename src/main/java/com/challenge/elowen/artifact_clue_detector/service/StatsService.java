package com.challenge.elowen.artifact_clue_detector.service;

import com.challenge.elowen.artifact_clue_detector.repository.ManuscriptRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {

    private final ManuscriptRepository repository;

    public StatsService(ManuscriptRepository repository) {
        this.repository = repository;
    }

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
