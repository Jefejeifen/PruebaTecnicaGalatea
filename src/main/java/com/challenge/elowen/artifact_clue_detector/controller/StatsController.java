package com.challenge.elowen.artifact_clue_detector.controller;

import com.challenge.elowen.artifact_clue_detector.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatsController {

    private final StatsService service;

    public StatsController(StatsService service) {
        this.service = service;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return service.getStats();
    }
}
