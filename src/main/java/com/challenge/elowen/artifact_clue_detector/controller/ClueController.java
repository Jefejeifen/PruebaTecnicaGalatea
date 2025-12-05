package com.challenge.elowen.artifact_clue_detector.controller;

import com.challenge.elowen.artifact_clue_detector.model.Manuscript;
import com.challenge.elowen.artifact_clue_detector.service.ArtifactClueService;
import com.challenge.elowen.artifact_clue_detector.util.ClueResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/clue")
public class ClueController {

    private final ArtifactClueService service;

    public ClueController(ArtifactClueService service) {
        this.service = service;
    }

    // Acepta tanto /clue como /clue/
    @PostMapping({"", "/"})
    public ResponseEntity<?> analyze(@RequestBody Map<String, String[]> request) {
        String[] manuscript = request.get("manuscript");

        if (manuscript == null || manuscript.length == 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "El campo 'manuscript' no puede estar vacío."
            ));
        }

        Manuscript result = service.analyzeAndSave(manuscript);

        var response = ClueResponseBuilder.buildResponse(result);

        return result.isHasClue()
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(403).body(response);
    }

}
