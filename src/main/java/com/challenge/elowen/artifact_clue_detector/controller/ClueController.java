package com.challenge.elowen.artifact_clue_detector.controller;

import com.challenge.elowen.artifact_clue_detector.model.Manuscript;
import com.challenge.elowen.artifact_clue_detector.service.ArtifactClueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (result.isHasClue()) {
            return ResponseEntity.ok(Map.of(
                    "hasClue", true,
                    "character", result.getClueCharacter(),
                    "direction", result.getClueDirection(),
                    "from", result.getFromPosition(),
                    "to", result.getToPosition()
            ));
        } else {
            return ResponseEntity.status(403).body(Map.of(
                    "hasClue", false,
                    "message", "No se encontró ninguna pista en el manuscrito."
            ));
        }
    }
}
